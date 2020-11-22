package com.github.ser.exception.badRequest;

import com.github.ser.enums.ErrorCodes;
import com.github.ser.exception.SerBadRequestException;

public class SPEquipmentUnavailableException extends SerBadRequestException {

    public SPEquipmentUnavailableException(String message) {
        super(message, ErrorCodes.SP_EQUIPMENT_UNAVAILABLE);
    }

}
