package com.github.ser.model.requests;

import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ChangeUserPersonalInfoRequest {

    private String fullName;

    private String phoneNumber;

}
