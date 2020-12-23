package com.github.ser.exception.badRequest;

import com.github.ser.enums.ErrorCodes;
import com.github.ser.exception.SerAuthException;

public class InvalidPasswordException extends SerAuthException {

    public InvalidPasswordException(String message) {
        super(message, ErrorCodes.INVALID_PASSWORD);
    }

}
