package com.github.ser.controller;

import com.github.ser.exception.SerAuthException;
import com.github.ser.exception.SerBadRequestException;
import com.github.ser.exception.SerRuntimeException;
import com.github.ser.model.response.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {SerBadRequestException.class})
    protected ResponseEntity<Object> handleConflict(SerBadRequestException ex, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(ex.getErrorCode())
                .message(ex.getMessage())
                .build();

        return handleExceptionInternal(ex,
                errorResponse,
                new HttpHeaders(),
                ex.getHttpStatus(),
                request);
    }

    @ExceptionHandler(value = SerAuthException.class)
    protected ResponseEntity<Object> handleConflict(SerAuthException ex, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(ex.getErrorCode())
                .message(ex.getMessage())
                .build();

        return handleExceptionInternal(ex,
                errorResponse,
                new HttpHeaders(),
                ex.getHttpStatus(),
                request);
    }

    @ExceptionHandler(value = SerRuntimeException.class)
    protected ResponseEntity<Object> handleConflict(SerRuntimeException ex, WebRequest request) {

        log.error(ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(ex.getErrorCode())
                .message(ex.getMessage())
                .build();

        return handleExceptionInternal(ex,
                errorResponse,
                new HttpHeaders(),
                ex.getHttpStatus(),
                request);
    }
}