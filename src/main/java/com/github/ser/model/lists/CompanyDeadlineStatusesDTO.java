package com.github.ser.model.lists;

import com.github.ser.model.dto.CompanyDeadlineStatusDTO;
import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class CompanyDeadlineStatusesDTO {
    private List<CompanyDeadlineStatusDTO> companyDeadlineStatuses;
    private int count;
}
