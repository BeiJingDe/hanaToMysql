package com.abc.hanatomysql.exception;

/**
 * @Author Z-7
 * @Date 2022/8/19
 */
public class MyClassCaseException extends ClassCastException{
    /**
     * 错误编码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String message;

    public MyClassCaseException (String message) {
        this.message = message;
    }
}
