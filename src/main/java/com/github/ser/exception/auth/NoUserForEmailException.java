package com.github.ser.exception.auth;

import com.github.ser.enums.ErrorCodes;
import com.github.ser.exception.SerBadRequestException;

public class NoUserForEmailException extends SerBadRequestException {

    public NoUserForEmailException(String message) {
        super(message, ErrorCodes.NO_USER_WITH_EMAIL);
    }

}
