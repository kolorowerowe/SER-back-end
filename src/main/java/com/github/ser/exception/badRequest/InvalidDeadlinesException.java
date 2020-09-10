package com.github.ser.exception.badRequest;

import com.github.ser.enums.ErrorCodes;
import com.github.ser.exception.SerBadRequestException;

public class InvalidDeadlinesException extends SerBadRequestException {

    public InvalidDeadlinesException(String message) {
        super(message, ErrorCodes.INVALID_DEADLINES);
    }

}
