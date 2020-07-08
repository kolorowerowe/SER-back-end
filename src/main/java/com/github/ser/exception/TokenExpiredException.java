package com.github.ser.exception;

import com.github.ser.enums.ErrorCodes;

public class TokenExpiredException extends SerRuntimeException {

    public TokenExpiredException(String message) {
        super(message, ErrorCodes.TOKEN_EXPIRED_ERROR);
    }

}
