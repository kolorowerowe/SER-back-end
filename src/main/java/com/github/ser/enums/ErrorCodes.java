package com.github.ser.enums;

public enum ErrorCodes {

    NO_USER_WITH_EMAIL_ERROR(101),
    INVALID_PASSWORD_ERROR(102);

    private final int code;

    ErrorCodes(int code) {
        this.code = code;
    }

    public int getCode(){
        return code;
    }
}
