package com.github.ser.exception;

import com.github.ser.enums.ErrorCodes;

public class SerRuntimeException extends RuntimeException{

    protected ErrorCodes errorCode;

    public SerRuntimeException(String message) {
        super(message);
    }

    public SerRuntimeException(String message, ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode.getCode();
    }

}
