package com.github.ser.exception.badRequest;

import com.github.ser.enums.ErrorCodes;
import com.github.ser.exception.SerBadRequestException;

public class NoSponsorshipPackageException extends SerBadRequestException {

    public NoSponsorshipPackageException(String message) {
        super(message, ErrorCodes.NO_SPONSORSHIP_FOR_PROVIDED_ID);
    }

}
