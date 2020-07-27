package com.github.ser.exception.badRequest;

import com.github.ser.enums.ErrorCodes;
import com.github.ser.exception.SerBadRequestException;

public class NoCompanyForUuidException extends SerBadRequestException {

    public NoCompanyForUuidException(String message) {
        super(message, ErrorCodes.NO_COMPANY_WITH_UUID);
    }

}
