package com.github.ser.exception;

import com.github.ser.enums.ErrorCodes;

public class NoUserForEmailException extends SerRuntimeException {

    public NoUserForEmailException(String message) {
        super(message, ErrorCodes.NO_USER_WITH_EMAIL_ERROR);
    }

}
