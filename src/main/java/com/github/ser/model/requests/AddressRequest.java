package com.github.ser.model.requests;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {

    @NotNull
    private String street;
    @NotNull
    private String buildingNumber;
    private String flatNumber;
    @NotNull
    private String city;
    @NotNull
    private String postalCode;
}
