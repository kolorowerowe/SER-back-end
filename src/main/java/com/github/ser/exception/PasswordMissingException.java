package com.github.ser.exception;

import com.github.ser.enums.ErrorCodes;

public class PasswordMissingException extends SerAuthException {

    public PasswordMissingException(String message) {
        super(message, ErrorCodes.PASSWORD_MISSING);
    }

}
