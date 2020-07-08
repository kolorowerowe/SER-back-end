package com.github.ser.enums;

public enum ErrorCodes {

    TOKEN_EXPIRED_ERROR(101),
    NO_USER_WITH_EMAIL_ERROR(201),
    INVALID_PASSWORD_ERROR(202);

    private final int code;

    ErrorCodes(int code) {
        this.code = code;
    }

    public int getCode(){
        return code;
    }
}
