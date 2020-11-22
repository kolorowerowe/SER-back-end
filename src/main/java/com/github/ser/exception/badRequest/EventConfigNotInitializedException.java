package com.github.ser.exception.badRequest;

import com.github.ser.enums.ErrorCodes;
import com.github.ser.exception.SerBadRequestException;

public class EventConfigNotInitializedException extends SerBadRequestException {

    public EventConfigNotInitializedException(String message) {
        super(message, ErrorCodes.EVENT_CONFIG_NOT_INITIALIZED);
    }

}
