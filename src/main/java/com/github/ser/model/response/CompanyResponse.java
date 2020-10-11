package com.github.ser.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.github.ser.model.database.Address;
import com.github.ser.model.database.Company;
import com.github.ser.model.database.User;
import com.github.ser.model.dto.CompanyDeadlineStatusDTO;
import com.github.ser.model.lists.CompanyDeadlineStatusesDTO;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.github.ser.util.DateTimeUtils.DATE_TIME_FORMAT;

@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Data
public class CompanyResponse {

    private UUID id;

    private UserMiniData primaryUser;

    private String name;

    private String contactPhone;

    private String taxId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime companyCreatedDate;

    private Address address;

    private SponsorshipPackageResponse sponsorshipPackage;

    private List<CompanyDeadlineStatusDTO> companyDeadlineStatuses;

    public CompanyResponse(Company company, User primaryUser, SponsorshipPackageResponse sponsorshipPackageResponse, CompanyDeadlineStatusesDTO companyDeadlineStatuses){
        this.id = company.getId();
        this.primaryUser = new UserMiniData(primaryUser);
        this.name = company.getName();
        this.contactPhone = company.getContactPhone();
        this.taxId = company.getTaxId();
        this.companyCreatedDate = company.getCompanyCreatedDate();
        this.address = company.getAddress();
        this.sponsorshipPackage = sponsorshipPackageResponse;
        this.companyDeadlineStatuses = companyDeadlineStatuses.getCompanyDeadlineStatuses();
    }

}
