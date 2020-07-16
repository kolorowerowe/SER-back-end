package com.github.ser.exception;

import com.github.ser.enums.ErrorCodes;
import org.springframework.http.HttpStatus;

public class SerBadRequestException extends RuntimeException {

    protected ErrorCodes errorCode;
    private HttpStatus httpStatus;

    public SerBadRequestException(String message, ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public Integer getErrorCode() {
        return errorCode.getCode();
    }

    public HttpStatus getHttpStatus(){
        return httpStatus;
    }

}
