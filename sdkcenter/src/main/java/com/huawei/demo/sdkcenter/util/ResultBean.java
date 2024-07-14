package com.huawei.demo.sdkcenter.util;

import com.huawei.demo.sdkcenter.entity.resp.SdkDetectTaskResp;

import java.util.List;

public class ResultBean<T> {
    private int code;
    private String message;
    private T data;
    private int total;


    public ResultBean() {
    }

    public ResultBean(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultBean(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }



    public ResultBean(int code, String message, T data, int total) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.total = total;
    }



    public String getMessage() {
        return message;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }



    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    // 静态方法：构建成功的响应
    public static <T> ResultBean<T> success() {
        return new ResultBean<>(200, "Success");
    }

    public static <T> ResultBean<T> success(T data) {
        return new ResultBean<>(200, "Success", data);
    }

    public static <T> ResultBean<T> success(String message, T data) {
        return new ResultBean<>(200, message, data);
    }

    // 静态方法：构建失败的响应
    public static <T> ResultBean<T> error() {
        return new ResultBean<>(500, "Error");
    }

    public static <T> ResultBean<T> error(String message) {
        return new ResultBean<>(500, message);
    }

    public static <T> ResultBean<T> error(int code, String message) {
        return new ResultBean<>(code, message);
    }

    public static <T> ResultBean<T> error(int code, String message, T data) {
        return new ResultBean<>(code, message, data);
    }
}