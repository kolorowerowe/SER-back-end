package com.github.ser.exception.runtime;

import com.github.ser.enums.ErrorCodes;
import com.github.ser.exception.SerRuntimeException;

public class MailNotSentException extends SerRuntimeException {

    public MailNotSentException(String message) {
        super(message, ErrorCodes.EMAIL_NOT_SENT);
    }

}
