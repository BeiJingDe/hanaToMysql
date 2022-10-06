package com.abc.hanatomysql.utils;

import com.abc.hanatomysql.config.HanaConfig;
import com.abc.hanatomysql.enums.ExecuteType;
import com.abc.hanatomysql.exception.ExecuteTypeException;
import com.abc.hanatomysql.exception.MyClassCaseException;
import com.abc.hanatomysql.model.HanaDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * hana工具类
 * @Author Z-7
 * @Date 2022/8/18
 */
@Slf4j
@Component
public class HanaUtil {
    // 线程副本，保证单个线程只获取一次连接
    public ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    @Autowired
    private HanaConfig hanaConfig;

    /**
     * 获取hana数据库连接
     * tips：每次调用都会重建连接
     * @return 数据库连接
     */
    public Connection getHanaConnection() {
        Connection connection = threadLocal.get();
        if (!Objects.isNull(connection)) {
            return connection;
        }
        try {
            log.info("=========================开始获取hana连接==========================");
            Class.forName(hanaConfig.getDriver());
            connection = DriverManager.getConnection(hanaConfig.getUrl(),hanaConfig.getUserName(),hanaConfig.getPassword());
            threadLocal.set(connection);
            log.info("=========================获取hana连接完成==========================");
        } catch (ClassNotFoundException e) {
            log.error("=========================hana驱动加载错误==========================");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            log.error("=========================hana数据库连接异常==========================");
            throw new RuntimeException("hana数据库连接异常");
        }
        return connection;
    }

    /**
     * 关闭连接
     */
    public void closeConnection() {
        try {
            threadLocal.get().close();
            threadLocal.remove();
            log.info("=========================关闭hana连接==========================");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 统一同步接口
     * @param hanaDTO hana查询对象
     * @param type 执行操作类型 SELETE
     * @return 返回list结果集
     * @param <T> 实体类型
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public <T> List<T> executeSelect(HanaDTO<T> hanaDTO, ExecuteType type) throws SQLException, InstantiationException, IllegalAccessException {
        if (!type.equals(ExecuteType.SELECT)) {
            throw new ExecuteTypeException("执行方式错误，请确认后执行");
        }
        List<T> list = new ArrayList<>();
        // 获取目标类反射对象
        Class<?> t = hanaDTO.getData();
        // 获取到属性长度
        Field[] fields = t.getDeclaredFields();
        // 获取数据库连接
        Connection connection = hanaDTO.getConnection();
        // 获取执行对象
        PreparedStatement preparedStatement = connection.prepareStatement(hanaDTO.getSql());
        // 执行sql
        log.info("=========================开始执行hana--->sql:" + hanaDTO.getSql());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            // 获取新对象
            Object o = t.newInstance();
            // 这里使用i 从 1 开始 要求：实体类第一个属性必须是版本号
            for (int i = 1; i < fields.length; i++) {
                // 暴力反射
                fields[i].setAccessible(true);
                fields[i].set(o,resultSet.getObject(i));
            }
            try {
                list.add((T) o);
            } catch (ClassCastException e) {
                throw new MyClassCaseException("请检查实体类与hana对应关系");
            }
        }
        log.info("=========================执行完毕==========================");
        return list;
    }
}
