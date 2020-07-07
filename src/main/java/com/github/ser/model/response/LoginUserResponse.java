package com.github.ser.model.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class LoginUserResponse {

    @NonNull
    private String authToken;

}
