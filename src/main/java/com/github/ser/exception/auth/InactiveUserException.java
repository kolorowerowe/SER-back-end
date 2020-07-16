package com.github.ser.exception.auth;

import com.github.ser.enums.ErrorCodes;
import com.github.ser.exception.SerAuthException;

public class InactiveUserException extends SerAuthException {

    public InactiveUserException(String message) {
        super(message, ErrorCodes.INACTIVE_USER);
    }

}
