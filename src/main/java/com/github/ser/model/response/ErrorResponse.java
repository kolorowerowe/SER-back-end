package com.github.ser.model.response;

import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ErrorResponse {

    private String message;
    private int errorCode;
    private Date timestamp;


}
