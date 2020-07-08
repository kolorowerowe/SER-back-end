package com.github.ser.exception;

import com.github.ser.enums.ErrorCodes;
import org.springframework.security.core.AuthenticationException;

public class SerAuthException extends AuthenticationException {

    protected ErrorCodes errorCode;

    public SerAuthException(String message, ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode.getCode();
    }

}
