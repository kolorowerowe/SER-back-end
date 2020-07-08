package com.github.ser.exception;

import com.github.ser.enums.ErrorCodes;

public class UsernameMissingException extends SerAuthException {

    public UsernameMissingException(String message) {
        super(message, ErrorCodes.USERNAME_MISSING);
    }

}
