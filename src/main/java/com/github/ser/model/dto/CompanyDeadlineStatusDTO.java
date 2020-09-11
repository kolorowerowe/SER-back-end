package com.github.ser.model.dto;

import com.github.ser.enums.DeadlineActivity;
import com.github.ser.model.database.Company;
import com.github.ser.model.database.Deadline;
import lombok.*;

import static com.github.ser.util.DeadlineUtils.isActivityDone;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class CompanyDeadlineStatusDTO {

    private Integer orderNumber;
    private DeadlineActivity activity;
    private String deadlineDate;
    private Boolean isFinished;

    public CompanyDeadlineStatusDTO(Deadline deadline) {
        this.orderNumber = deadline.getOrderNumber();
        this.activity = deadline.getActivity();
        this.deadlineDate = deadline.getDeadlineDate();
    }

    public static CompanyDeadlineStatusDTO getCompanyDeadlineStatusDTO(Deadline deadline, Company company) {
        CompanyDeadlineStatusDTO companyStatus = new CompanyDeadlineStatusDTO(deadline);
        companyStatus.setIsFinished(isActivityDone(deadline.getActivity(), company));
        return companyStatus;
    }

}