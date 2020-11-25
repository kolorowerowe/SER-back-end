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

    private String street;
    private String buildingNumber;
    private String flatNumber;
    private String city;
    private String postalCode;
}
