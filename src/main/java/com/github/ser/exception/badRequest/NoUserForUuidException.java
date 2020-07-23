package com.github.ser.exception.badRequest;

import com.github.ser.enums.ErrorCodes;
import com.github.ser.exception.SerBadRequestException;

public class NoUserForUuidException extends SerBadRequestException {

    public NoUserForUuidException(String message) {
        super(message, ErrorCodes.NO_USER_WITH_UUID);
    }

}
