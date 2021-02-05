package com.hitenine.blog.response;

/**
 * @author Hitenine
 * @version 1.0
 * @date 2021/1/28 21:23
 */
public enum ResponseState {
    SUCCESS(true, 20000, "操作成功"),
    LOGIN_SUCCESS(true, 20001, "登录成功"),
    JOIN_IN_SUCCESS(true, 20001, "注册成功"),
    FAILED(false, 40000, "操作失败"),
    GET_RESOURCE_FAILED(false, 40001, "获取资源失败"),
    ACCOUNT_NOT_LOGIN(false, 40002, "账号未登录"),
    PERMISSION_DENIED(false, 40003, "无权限访问"),
    ACCOUNT_DENIED(false, 40004, "账号被禁止"),
    LOGIN_FAILED(false, 49999, "登陆失败"),

    FORBIDDEN(false, 403, "无权限访问"),
    NOT_FOUND(false, 404, "页面丢失"),
    GATEWAY_TIMEOUT(false, 504, "系统繁忙，请稍后重试"),
    HTTP_VERSION_NOT_SUPPORTED(false, 505, "请求错误，请检查所提交数据");

    private Boolean success;
    private Integer code;
    private String message;

    ResponseState(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String getMessage(String name) {
        for (ResponseState item : ResponseState.values()) {
            if (item.name().equals(name)) {
                return item.message;
            }
        }
        return null;
    }

    public static Integer getCode(String name) {
        for (ResponseState item : ResponseState.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }
}
