package com.example.demo.common;

/**
 * @author zhaoyu
 * @date 2019-08-21
 */
public class Proto<T> {
    private int code;
    private String msg;
    private T data;

    public Proto() {
    }

    public Proto(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Proto(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String toString() {
        return "Result:code:[" + this.code + "],msg:[" + this.msg + "]";
    }
}
