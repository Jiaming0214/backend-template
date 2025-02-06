package com.ming.entity;

public class RestBean<T> {
    private Integer status;
    private Boolean success;
    private T data;

    private RestBean(Integer status, Boolean success, T data) {
        this.status = status;
        this.success = success;
        this.data = data;
    }

    public static <T> RestBean<T> success() {
        return new RestBean<>(200, true, null);
    }

    public static <T> RestBean<T> success(T data) {
        return new RestBean<>(200, true, data);
    }

    public static <T> RestBean<T> failure(Integer status) {
        return new RestBean<>(status, false, null);
    }

    public static <T> RestBean<T> failure(Integer status, T data) {
        return new RestBean<>(status, false, data);
    }

    public Integer getStatus() {
        return status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }
}
