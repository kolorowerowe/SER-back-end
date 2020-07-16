package com.github.ser.exception.auth;

import com.github.ser.enums.ErrorCodes;
import com.github.ser.exception.SerBadRequestException;

public class InvalidPasswordException extends SerBadRequestException {

    public InvalidPasswordException(String message) {
        super(message, ErrorCodes.INVALID_PASSWORD);
    }

}
