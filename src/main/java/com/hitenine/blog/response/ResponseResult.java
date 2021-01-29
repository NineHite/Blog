package com.hitenine.blog.response;

/**
 * @author Hitenine
 * @date 2021/1/28 21:14
 * @version 1.0
 */
public class ResponseResult<T> {
    private boolean success;
    private int code;
    private String message;
    private T data;

    public static <T> ResponseResult<T> SUCCESS() {
        return new ResponseResult<>(ResponseState.SUCCESS);
    }

    public static <T> ResponseResult<T> SUCCESS(String message) {
        ResponseResult<T> responseResult = new ResponseResult<>(ResponseState.SUCCESS);
        responseResult.setMessage(message);
        return responseResult;
    }

    public static <T> ResponseResult<T> SUCCESS(ResponseState responseState, T data) {
        return new ResponseResult(ResponseState.SUCCESS).setData(data);
    }

    public static <T> ResponseResult<T> FAILED() {
        return new ResponseResult<>(ResponseState.FAILED);
    }

    public static <T> ResponseResult<T> FAILED(String message) {
        ResponseResult<T> responseResult = new ResponseResult<T>(ResponseState.FAILED);
        responseResult.setMessage(message);
        return responseResult;
    }

    public ResponseResult() {
    }

    public ResponseResult(ResponseState responseState) {
        this.success = responseState.isSuccess();
        this.code = responseState.getCode();
        this.message = responseState.getMessage();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public ResponseResult setData(T data) {
        this.data = data;
        return this;
    }
}