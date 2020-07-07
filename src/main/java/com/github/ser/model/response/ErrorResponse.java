package com.github.ser.model.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ErrorResponse {

    private String message;
    private int errorCode;


}
