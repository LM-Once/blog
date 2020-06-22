package com.it.Common;

public class RetResult<T> {

    int code;

    String smg;

    T data;

    public RetResult() {
    }

    public RetResult(int code, String smg, T data) {
        this.code = code;
        this.smg = smg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public RetResult setCode(int code) {
        this.code = code;
        return this;
    }

    public String getSmg() {
        return smg;
    }

    public RetResult setSmg(String smg) {
        this.smg = smg;
        return this;
    }

    public T getData() {
        return data;
    }

    public RetResult setData(T data) {
        this.data = data;
        return this;
    }
}
