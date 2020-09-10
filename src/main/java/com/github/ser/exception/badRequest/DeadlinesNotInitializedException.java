package com.github.ser.exception.badRequest;

import com.github.ser.enums.ErrorCodes;
import com.github.ser.exception.SerBadRequestException;

public class DeadlinesNotInitializedException extends SerBadRequestException {

    public DeadlinesNotInitializedException(String message) {
        super(message, ErrorCodes.DEADLINES_NOT_INITIALIZED);
    }

}
