package com.github.ser.exception.badRequest;

import com.github.ser.enums.ErrorCodes;
import com.github.ser.exception.SerBadRequestException;

public class InvalidRepeatPasswordException extends SerBadRequestException {

    public InvalidRepeatPasswordException(String message) {
        super(message, ErrorCodes.PASSWORD_DOES_NOT_MATCH);
    }

}
