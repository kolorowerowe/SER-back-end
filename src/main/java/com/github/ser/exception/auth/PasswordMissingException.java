package com.github.ser.exception.auth;

import com.github.ser.enums.ErrorCodes;
import com.github.ser.exception.SerAuthException;

public class PasswordMissingException extends SerAuthException {

    public PasswordMissingException(String message) {
        super(message, ErrorCodes.PASSWORD_MISSING);
    }

}
