package com.abc.hanatomysql.exception.handler;

import cn.hutool.http.HttpStatus;
import com.abc.hanatomysql.common.Result;
import com.abc.hanatomysql.exception.ExecuteTypeException;
import com.abc.hanatomysql.exception.MyClassCaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 * @Author Z-7
 * @Date 2022/8/19
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionlHandler {
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e) {
        log.error(e.getMessage());
        return Result.faild("请联系管理员查看");
    }

    @ExceptionHandler(ExecuteTypeException.class)
    public Result executeTypeExceptionHandler(ExecuteTypeException e) {
        log.error(e.getMessage());
        return Result.faild(HttpStatus.HTTP_INTERNAL_ERROR,"执行操作异常",e.getMessage());
    }

    @ExceptionHandler(MyClassCaseException.class)
    public Result myClassCaseExceptionHandler(MyClassCaseException e) {
        log.error(e.getMessage());
        return Result.faild(HttpStatus.HTTP_INTERNAL_ERROR,"类型转换异常",e.getMessage());
    }
}
