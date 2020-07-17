package com.github.ser.exception.badRequest;

import com.github.ser.enums.ErrorCodes;
import com.github.ser.exception.SerBadRequestException;

public class InvalidOldPasswordException extends SerBadRequestException {

    public InvalidOldPasswordException(String message) {
        super(message, ErrorCodes.INVALID_OLD_PASSWORD);
    }

}
