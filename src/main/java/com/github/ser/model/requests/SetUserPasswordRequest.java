package com.github.ser.model.requests;

import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class SetUserPasswordRequest {

    @NonNull
    private String newPassword;

    @NonNull
    private String repeatNewPassword;

}
