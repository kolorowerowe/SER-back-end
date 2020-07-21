package com.github.ser.model.requests;

import com.github.ser.enums.Role;
import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class RegisterUserRequest {

    @NonNull
    private String email;

    private String fullName;

    private String phoneNumber;

    private Boolean shouldChangePassword;

    private Role role;

}
