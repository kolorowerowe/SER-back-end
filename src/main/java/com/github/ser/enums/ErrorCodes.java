package com.github.ser.enums;

public enum ErrorCodes {

    // 0 - general error
    UNEXPECTED(0),
    EMAIL_NOT_SENT(1),
    CONVERT_TO_CSV_FAILED(2),

    // 1 - auth error
    TOKEN_EXPIRED(100),

    // 2 - login user error
    NO_USER_FOR_PROVIDED_ID(200),
    NO_USER_FOR_PROVIDED_EMAIL(201),
    USERNAME_MISSING(202),
    PASSWORD_MISSING(203),
    INVALID_PASSWORD(204),
    INACTIVE_USER(205),
    CREDENTIALS_INVALID(206),
    INVALID_VERIFICATION_CODE(207),
    USER_ALREADY_ACTIVATED(208),

    INVALID_OLD_PASSWORD(251),
    PASSWORD_DOES_NOT_MATCH(252),

    // 3 - company error
    NO_COMPANY_FOR_PROVIDED_ID(300),

    NO_SPONSORSHIP_FOR_PROVIDED_ID(350),
    NO_EQUIPMENT_FOR_PROVIDED_ID(351),
    NO_SP_EQUIPMENT_FOR_PROVIDED_ID(352),
    SP_EQUIPMENT_UNAVAILABLE(353),

    // 4 - admin errors
    INVALID_DEADLINES(400),
    DEADLINES_NOT_INITIALIZED(401),
    EVENT_CONFIG_NOT_INITIALIZED(402),
    EVENT_CONFIG_INVALID(403);


    private final int code;

    ErrorCodes(int code) {
        this.code = code;
    }

    public int getCode(){
        return code;
    }
}
