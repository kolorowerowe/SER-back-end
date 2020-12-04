package com.github.ser.exception.runtime;

import com.github.ser.enums.ErrorCodes;
import com.github.ser.exception.SerRuntimeException;

public class ConvertToCsvFailedException extends SerRuntimeException {

    public ConvertToCsvFailedException(String message) {
        super(message, ErrorCodes.CONVERT_TO_CSV_FAILED);
    }

}
