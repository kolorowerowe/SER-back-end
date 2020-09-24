package com.github.ser.exception.badRequest;

import com.github.ser.enums.ErrorCodes;
import com.github.ser.exception.SerBadRequestException;

public class NoEquipmentException extends SerBadRequestException {

    public NoEquipmentException(String message) {
        super(message, ErrorCodes.NO_EQUIPMENT_FOR_PROVIDED_ID);
    }

}
