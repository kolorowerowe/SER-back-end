package com.github.ser.model.statistics;


import com.github.ser.model.response.SponsorshipPackageResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SponsorshipPackageStatistics {

    private Integer allSPCount;
    private List<PercentageProgress<SponsorshipPackageResponse>> percentageProgressesSP;


}

