package com.github.ser.exception.auth;

import com.github.ser.enums.ErrorCodes;
import com.github.ser.exception.SerAuthException;

public class UsernameMissingException extends SerAuthException {

    public UsernameMissingException(String message) {
        super(message, ErrorCodes.USERNAME_MISSING);
    }

}
