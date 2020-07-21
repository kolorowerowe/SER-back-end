package com.github.ser.exception.auth;

import com.github.ser.enums.ErrorCodes;
import com.github.ser.exception.SerAuthException;

public class InvalidVerificationCodeException extends SerAuthException {

    public InvalidVerificationCodeException(String message) {
        super(message, ErrorCodes.INVALID_VERIFICATION_CODE);
    }

}
