package com.github.ser.model.lists;

import com.github.ser.model.response.SponsorshipPackageResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class SponsorshipPackageListResponse {
    private List<SponsorshipPackageResponse> sponsorshipPackageList;
    private int count;
}
