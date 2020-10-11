package com.github.ser.exception.badRequest;

import com.github.ser.enums.ErrorCodes;
import com.github.ser.exception.SerBadRequestException;

public class NoSPEquipmentException extends SerBadRequestException {

    public NoSPEquipmentException(String message) {
        super(message, ErrorCodes.NO_SP_EQUIPMENT_FOR_PROVIDED_ID);
    }

}
