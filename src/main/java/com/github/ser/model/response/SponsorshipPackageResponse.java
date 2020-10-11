package com.github.ser.model.response;

import com.github.ser.model.database.Price;
import com.github.ser.model.database.SPEquipment;
import com.github.ser.model.database.Translation;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@With
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class SponsorshipPackageResponse {


    private UUID id;

    private Set<Translation> translations;

    private Set<Price> prices;

    private Double standSize;

    private Boolean isAvailable;

    private List<SPEquipment> spEquipmentList;
}
