package com.abc.hanatomysql.exception;

/**
 * @Author Z-7
 * @Date 2022/8/18
 */
public class ExecuteTypeException extends RuntimeException{

    /**
     * 错误编码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String message;

    public ExecuteTypeException (String message) {
        this.message = message;
    }
}
