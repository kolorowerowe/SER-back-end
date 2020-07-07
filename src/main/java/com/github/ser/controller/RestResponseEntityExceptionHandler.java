package com.github.ser.controller;

import com.github.ser.exception.InvalidPasswordException;
import com.github.ser.exception.NoUserForEmailException;
import com.github.ser.exception.SerRuntimeException;
import com.github.ser.model.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {InvalidPasswordException.class, NoUserForEmailException.class})
    protected ResponseEntity<Object> handleConflict(SerRuntimeException ex, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(ex.getErrorCode())
                .message(ex.getMessage())
                .build();

        return handleExceptionInternal(ex,
                errorResponse,
                new HttpHeaders(),
                HttpStatus.UNAUTHORIZED,
                request);
    }
}