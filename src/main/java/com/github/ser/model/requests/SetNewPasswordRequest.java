package com.github.ser.model.requests;

import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class SetNewPasswordRequest {

    @NonNull
    private String email;

    @NonNull
    private String newPassword;

    @NonNull
    private String repeatNewPassword;

}
