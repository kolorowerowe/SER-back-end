package com.github.ser.exception.badRequest;

import com.github.ser.enums.ErrorCodes;
import com.github.ser.exception.SerBadRequestException;

public class EventConfigInvalidException extends SerBadRequestException {

    public EventConfigInvalidException(String message) {
        super(message, ErrorCodes.EVENT_CONFIG_INVALID);
    }

}
