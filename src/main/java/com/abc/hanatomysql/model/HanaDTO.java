package com.abc.hanatomysql.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.sql.Connection;

/**
 * @Author Z-7
 * @Date 2022/8/18
 */
@Data
@ApiModel(value = "供hanaUtil使用")
public class HanaDTO<T> implements Serializable {
    /**
     * 连接对象
     */
    private Connection connection;

    /**
     * 要执行的sql
     */
    private String sql;

    /**
     * 结果反射对象
     */
    private Class<T> data;
}
