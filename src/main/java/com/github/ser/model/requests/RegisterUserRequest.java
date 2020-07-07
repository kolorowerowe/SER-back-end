package com.github.ser.model.requests;

import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class RegisterUserRequest {

    @NonNull
    private String email;

    private String password;

    private String repeatPassword;

    private String fullName;

    private String phoneNumber;

    private Boolean shouldChangePassword;

}
