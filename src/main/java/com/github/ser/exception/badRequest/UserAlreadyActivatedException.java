package com.github.ser.exception.badRequest;

import com.github.ser.enums.ErrorCodes;
import com.github.ser.exception.SerBadRequestException;

public class UserAlreadyActivatedException extends SerBadRequestException {

    public UserAlreadyActivatedException(String message) {
        super(message, ErrorCodes.USER_ALREADY_ACTIVATED);
    }

}
