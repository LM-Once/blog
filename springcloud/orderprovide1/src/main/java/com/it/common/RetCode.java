package com.it.common;

public enum RetCode {

    SUCCCESS(200, "success"),
    ERRORPARAM(401,"param error"),
    NO_LOGIN(401,"please login first."),
     FAIL(400, "fail"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404 , "Not found");

    public int code;

    public String msg;

    RetCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
