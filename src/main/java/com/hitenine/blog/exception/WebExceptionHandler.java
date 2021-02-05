package com.hitenine.blog.exception;

import com.hitenine.blog.response.ResponseResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Hitenine
 * @version 1.0
 * @date 2021/2/4 20:37
 */
// @RestControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseResult notLogin(Exception e, HttpServletRequest request) {
        return ResponseResult.ACCOUNT_NOT_LOGIN();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseResult error403(Exception e, HttpServletRequest request) {
        return ResponseResult.PERMISSION_DENIED();
    }
}
