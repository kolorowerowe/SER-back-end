package com.github.ser.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.github.ser.model.database.Address;
import com.github.ser.model.database.CatalogInformation;
import com.github.ser.model.database.Company;
import com.github.ser.model.database.User;
import com.github.ser.model.dto.CompanyDeadlineStatusDTO;
import com.github.ser.model.lists.CompanyDeadlineStatusesDTO;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Arrays;
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

    private Address address;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime companyCreatedDate;


    private SponsorshipPackageResponse sponsorshipPackage;

    private CatalogInformation catalogInformation;

    private List<CompanyDeadlineStatusDTO> companyDeadlineStatuses;

    public CompanyResponse(Company company, User primaryUser, SponsorshipPackageResponse sponsorshipPackageResponse, CompanyDeadlineStatusesDTO companyDeadlineStatuses) {
        this.id = company.getId();
        this.primaryUser = new UserMiniData(primaryUser);
        this.name = company.getName();
        this.contactPhone = company.getContactPhone();
        this.taxId = company.getTaxId();
        this.companyCreatedDate = company.getCompanyCreatedDate();
        this.address = company.getAddress();
        this.sponsorshipPackage = sponsorshipPackageResponse;
        this.catalogInformation = company.getCatalogInformation();
        this.companyDeadlineStatuses = companyDeadlineStatuses.getCompanyDeadlineStatuses();
    }

    public static List<String> getHeaders() {
        return Arrays.asList(
                "ID",
                "Primary User ID",
                "Primary User Email",
                "Primary User Full Name",
                "Company Name",
                "Contact phone",
                "Tax ID",
                "Street",
                "Building number",
                "Flat number",
                "City",
                "Postal code",
                "Company created Date",
                "Sponsorship package",
                "Catalog information filled");
    }

    public List<String> getRow() {
        return Arrays.asList(
                this.id.toString(),
                this.primaryUser.getId().toString(),
                this.primaryUser.getEmail(),
                this.primaryUser.getFullName(),
                this.name,
                this.contactPhone,
                this.taxId,
                this.address.getStreet(),
                this.address.getBuildingNumber(),
                this.address.getFlatNumber(),
                this.address.getCity(),
                this.address.getPostalCode(),
                this.companyCreatedDate.toString(),
                this.sponsorshipPackage != null ? this.sponsorshipPackage.getId().toString() : null,
                this.catalogInformation != null ? "YES" : "NO"
        );
    }

}
