package com.gyb.shiro.config;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author geng
 * 2020/10/16
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String exceptionHandle(Exception e){
        if(e instanceof AuthorizationException){
            return "nopermission";
        }
        return "exception";
    }
}
