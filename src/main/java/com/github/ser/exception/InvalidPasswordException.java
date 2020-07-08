package com.github.ser.exception;

import com.github.ser.enums.ErrorCodes;

public class InvalidPasswordException extends SerRuntimeException {

    public InvalidPasswordException(String message) {
        super(message, ErrorCodes.INVALID_PASSWORD);
    }

}
