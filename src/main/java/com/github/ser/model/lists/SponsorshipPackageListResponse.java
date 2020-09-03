package com.github.ser.model.lists;

import com.github.ser.model.database.SponsorshipPackage;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class SponsorshipPackageListResponse {
    private List<SponsorshipPackage> sponsorshipPackageList;
    private int count;
    private String error;

}
