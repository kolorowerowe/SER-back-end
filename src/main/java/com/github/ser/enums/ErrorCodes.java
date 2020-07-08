package com.github.ser.enums;

public enum ErrorCodes {

    // 1 - general error
    UNEXPECTED(0),

    // 2 - auth error
    TOKEN_EXPIRED(100),
    NO_USER_WITH_EMAIL(201),
    USERNAME_MISSING(201),
    PASSWORD_MISSING(203),
    INVALID_PASSWORD(204),
    INACTIVE_USER(205),
    CREDENTIALS_INVALID(206);

    private final int code;

    ErrorCodes(int code) {
        this.code = code;
    }

    public int getCode(){
        return code;
    }
}
