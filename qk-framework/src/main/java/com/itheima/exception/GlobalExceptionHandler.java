package com.itheima.exception;

import com.itheima.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result handleException(BusinessException e) {
        log.info("异常", e);
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // 捕获指定类型异常
    public Result handlerException(MethodArgumentNotValidException e) {

        String msg = e.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(","));

        return Result.fail(msg);
    }

    @ExceptionHandler(DuplicateKeyException.class) // 处理重复键异常（如手机号、用户名、邮箱重复）
    public Result handlerException(DuplicateKeyException e) {
        log.warn("唯一字段重复", e);
        return Result.fail("数据已存在，手机号/用户名/邮箱等唯一字段不能重复");
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error("异常", e);
        return Result.fail("服务器错误");
    }
}
