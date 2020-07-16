package com.github.ser.exception.auth;

import com.github.ser.enums.ErrorCodes;
import com.github.ser.exception.SerRuntimeException;

public class TokenExpiredException extends SerRuntimeException {

    public TokenExpiredException(String message) {
        super(message, ErrorCodes.TOKEN_EXPIRED);
    }

}
