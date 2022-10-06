package com.abc.hanatomysql.common;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Z-7
 * @Date 2022/8/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "统一结果返回类")
public class Result<T> {
    /**
     * 是否成功
     */
    private Boolean result;
    /**
     * 响应编码
     */
    private Integer code;
    /**
     * 信息
     */
    private String msg;
    /**
     * 返回的数据
     */
    private T data;

    public static Result successd() {
        return new Result(true, 200, null, null);
    }

    public static Result successd(Object data) {
        return new Result(true, 200, null, data);
    }

    public static Result faild(String message) {
        return new Result(false, 500, message, null);
    }

    public static Result faild(Integer errorCode, String message) {
        return new Result(false, errorCode, message, null);
    }

    public static Result faild(Integer errorCode, String message, Object data) {
        return new Result(false, errorCode, message, data);
    }
}
