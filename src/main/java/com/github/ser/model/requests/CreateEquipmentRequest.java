package com.github.ser.model.requests;

import com.github.ser.model.database.Price;
import com.github.ser.model.database.Translation;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class CreateEquipmentRequest {

    private Set<Translation> translations;
    private Set<Price> prices;
    private Integer maxCountPerCompany;

}
