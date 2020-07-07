package com.github.ser.exception;

import com.github.ser.enums.ErrorCodes;

public class InvalidPasswordException extends SerRuntimeException {

    public InvalidPasswordException(String message) {
        super(message, ErrorCodes.NO_USER_WITH_EMAIL_ERROR);
    }

}
