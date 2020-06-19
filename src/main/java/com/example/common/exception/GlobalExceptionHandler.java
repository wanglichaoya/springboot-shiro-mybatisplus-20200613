package com.example.common.exception;

import com.example.common.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2020/6/13
 * Time: 下午 08:53
 * Description:全局异常处理
 */
// @ControllerAdvice表示定义全局控制器异常处理
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕捉shiro 异常
     **/
    public JsonResult handle401(ShiroException e) {
        return JsonResult.fail(e.getMessage(), null, 401);

    }

    /**
     * 处理Assert 的异常
     **/
    // @ResponseStatus注解的异常类会被ResponseStatusExceptionResolver 解析
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    // @ExceptionHandler表示针对性异常处理，可对每种异常针对性处理。
    @ExceptionHandler(value = IllegalArgumentException.class)
    public JsonResult handler(IllegalArgumentException e) {

        return JsonResult.fail(e.getMessage());
    }

    /**
     * 校验错误异常
     *
     * **/
    public JsonResult handler(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        return JsonResult.fail(objectError.getDefaultMessage());
    }
    /**
     * 运行时异常
     * **/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public JsonResult handler(RuntimeException e) {
        log.error("运行时异常：----------------{}", e);
        return JsonResult.fail(e.getMessage());
    }




}
