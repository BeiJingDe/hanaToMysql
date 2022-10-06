package com.abc.hanatomysql.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 统一同步接口参数
 * @Author Z-7
 * @Date 2022/8/19
 */
@Data
@ApiModel(value = "统一同步接口参数")
public class SyncDTO {
    /**
     * 要执行的sql
     */
    private String sql;

    /**
     * 要转成的实体类名称
     */
    private String className;
}
