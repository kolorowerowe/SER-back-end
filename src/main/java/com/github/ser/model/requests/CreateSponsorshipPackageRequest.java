package com.github.ser.model.requests;

import com.github.ser.model.database.Price;
import com.github.ser.model.database.SponsorshipPackageTranslation;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class CreateSponsorshipPackageRequest {

    private Set<SponsorshipPackageTranslation> translations;
    private Set<Price> prices;
    private Double standSize;

}
