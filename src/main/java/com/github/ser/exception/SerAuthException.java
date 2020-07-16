package com.github.ser.exception;

import com.github.ser.enums.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

public class SerAuthException extends AuthenticationException {

    protected ErrorCodes errorCode;
    private HttpStatus httpStatus;

    public SerAuthException(String message, ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = HttpStatus.FORBIDDEN;

    }

    public Integer getErrorCode() {
        return errorCode.getCode();
    }

    public HttpStatus getHttpStatus(){
        return httpStatus;
    }
}
