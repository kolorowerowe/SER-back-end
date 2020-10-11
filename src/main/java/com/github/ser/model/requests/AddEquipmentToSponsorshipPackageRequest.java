package com.github.ser.model.requests;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class AddEquipmentToSponsorshipPackageRequest {

    private UUID equipmentId;
    private Integer count;

}
