package com.abc.hanatomysql.service.impl;

import com.abc.hanatomysql.common.Result;
import com.abc.hanatomysql.enums.ExecuteType;
import com.abc.hanatomysql.model.HanaDTO;
import com.abc.hanatomysql.model.SyncDTO;
import com.abc.hanatomysql.service.IGlobalService;
import com.abc.hanatomysql.utils.HanaUtil;
import com.abc.hanatomysql.utils.SpringUtils;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.*;
import java.util.stream.Stream;

/**
 * 统一接口服务层
 * @Author Z-7
 * @Date 2022/8/19
 */
@Slf4j
@Service
public class GlobalServiceImpl implements IGlobalService {
    @Autowired
    private HanaUtil hanaUtil;

    /**
     * 同步数据
     * @param syncDTO 传输对象
     * @return 统一结果
     */
    @Override
    public Result hanaToMysql(SyncDTO syncDTO) {
        // 通过类型获取对象
        String className = syncDTO.getClassName();
        // 接口名称
        String serviceName = className + "Service";
        // bean名称
        String beanName = className.toLowerCase() + "ServiceImpl";
        // 执行结果
        Boolean invoke = false;
        Connection connection = null;
        try {
            Class<?> forName = Class.forName("com.abc.hanatomysql.entity." + className);
            // 获取表的主键字段名称
            String idName = getTableIdName(forName);
            // 查询hana库
            connection = hanaUtil.getHanaConnection();
            HanaDTO hanaDTO = new HanaDTO();
            hanaDTO.setData(forName);
            hanaDTO.setConnection(connection);
            hanaDTO.setSql(syncDTO.getSql());
            // 获取结果集
            List list = hanaUtil.executeSelect(hanaDTO, ExecuteType.SELECT);
            // 获取要操作的service接口Class对象
            Class<?> service = Class.forName("com.abc.hanatomysql.service.I" + serviceName);
            // 操作数据库
            invoke = operateMysql(service, idName, beanName, list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (!invoke) {
            return Result.faild("同步失败");
        }
        return Result.successd();
    }

    /**
     * 获取数据库连接
     */
    @Override
    public void getConnetion() {
        Connection hanaConnection = hanaUtil.getHanaConnection();
        if (hanaConnection == null) {
            throw new RuntimeException("获取数据库连接异常");
        }
    }

    /**
     * 关闭数据库连接
     */
    @Override
    public void close() {
        hanaUtil.closeConnection();
    }

    /**
     * 通过类对象获取表主键id
     * @param clazz 实体类对象 (必须对应mysql表)
     * @return id名称
     */
    public String getTableIdName(Class<?> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        Stream<Field> fieldStream = Arrays.stream(declaredFields).filter(field -> Objects.nonNull(field.getAnnotation(TableId.class)));
        Optional<Field> first = fieldStream.findFirst();
        Field field = first.get();
        String name = field.getName();
        Field id = null;
        try {
            id = clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        TableId annotation = id.getAnnotation(TableId.class);
        return annotation.value();
    }

    /**
     * 操作数据库
     * @param service 要操作的service接口反射对象
     * @param idName 表主键明名称
     * @param beanName spring中bean名称
     * @param list 集合数据
     * @return 返回是否成功
     * @throws NoSuchMethodException 未找到方法
     * @throws InvocationTargetException 调用目标异常
     * @throws IllegalAccessException 非法访问例外
     */
    @Transactional
    public Boolean operateMysql(Class<?> service, String idName, String beanName, List list) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object serviceInstance = SpringUtils.getBean(beanName);
        // 删除表中所有
        Method removeAll = service.getMethod("remove", Wrapper.class);
        log.info("=========================开始删除表中数据==========================");
        removeAll.invoke(serviceInstance, new QueryWrapper().isNotNull(idName));
        log.info("=========================删除表数据完成==========================");
        // 批量更新
        log.info("=========================开始更新表中数据==========================");
        Method saveBatch = service.getMethod("saveBatch", Collection.class);
        Boolean result = (Boolean) saveBatch.invoke(serviceInstance, list);
        log.info("=========================更新表数据完成==========================");
        return result;
    }
}
