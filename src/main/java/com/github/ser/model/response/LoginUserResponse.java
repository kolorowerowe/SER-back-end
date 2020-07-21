package com.github.ser.model.response;

import com.github.ser.model.database.User;
import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class LoginUserResponse {

    @NonNull
    private String authToken;

    private User user;

}
