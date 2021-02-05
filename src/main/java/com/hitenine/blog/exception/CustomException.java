package com.hitenine.blog.exception;

import com.hitenine.blog.response.ResponseState;

/**
 * 自定义异常
 *
 * @author Hitenine
 * @date 2021/2/4 21:05
 * @version 1.0
 */
public class CustomException extends Exception {
    private Integer code;
    private String message;

    public CustomException(ResponseState responseState) {
        this.code = responseState.getCode();
        this.message = responseState.getMessage();
    }
}
