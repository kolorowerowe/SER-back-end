package com.github.ser.exception;

import com.github.ser.enums.ErrorCodes;
import org.springframework.http.HttpStatus;

public class SerRuntimeException extends RuntimeException{

    protected final ErrorCodes errorCode;
    private final HttpStatus httpStatus;

    public SerRuntimeException(String message, ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    }

    public Integer getErrorCode() {
        return errorCode.getCode();
    }

    public HttpStatus getHttpStatus(){
        return httpStatus;
    }

}
