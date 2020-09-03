package com.github.ser.model.requests;

import com.github.ser.model.database.Price;
import com.github.ser.model.database.SponsorshipPackageTranslation;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ChangeSponsorshipPackageRequest {

    private List<SponsorshipPackageTranslation> translations;
    private List<Price> prices;
    private Double standSize;
    private Boolean isAvailable;

}
