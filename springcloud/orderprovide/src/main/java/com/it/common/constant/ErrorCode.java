package com.it.common.constant;

public enum ErrorCode {
    USERNAME_PASSWORD_ERROR("username or password error"),
    NO_TOKEN_ERROR("no token error"),
    USER_NOT_LOGIN("not login"),
    PASSWORD_NOT_NULL("password_not_null"),
    USER_NOT_NULL("username not null");

    String errorMsg;

    ErrorCode(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
