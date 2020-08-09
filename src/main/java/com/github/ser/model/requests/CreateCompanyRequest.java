package com.github.ser.model.requests;

import lombok.*;

import javax.validation.Valid;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class CreateCompanyRequest {


    private UUID primaryUserId;
    private String name;
    private String contactPhone;
    private String taxId;

    @Valid
    private AddressRequest address;

}
