package com.github.ser.exception;

import com.github.ser.enums.ErrorCodes;

public class InactiveUserException extends SerAuthException {

    public InactiveUserException(String message) {
        super(message, ErrorCodes.INACTIVE_USER);
    }

}
