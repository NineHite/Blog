package com.hitenine.blog.response;

/**
 * @author Hitenine
 * @date 2021/1/28 21:23
 * @version 1.0
 */
public enum ResponseState {
    SUCCESS(true, 20000, "操作成功"),
    LOGIN_SUCCESS(true, 20001, "登录成功"),
    FAILED(false, 40000, "操作失败"),
    GET_RESOURCE_FAILED(false, 40001, "获取资源失败"),
    LOGIN_FAILED(false, 49999, "登陆失败");

    private boolean success;
    private int code;
    private String message;

    ResponseState(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
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
}
