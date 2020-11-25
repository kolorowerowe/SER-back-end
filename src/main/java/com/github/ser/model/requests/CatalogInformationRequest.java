package com.github.ser.model.requests;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CatalogInformationRequest {

    private String companyName;
    private String description;
    private String email;
    private String website;
    private String businessProfile;

    private Integer numberOfEmployeesPoland;
    private Integer numberOfEmployeesWorldwide;

    private String candidateRequirements;
    private String mainOfficeLocation;
    private String subsidiaryOfficesLocations;
    private Integer numberOfJobVacancies;

    private Boolean paidInternships;
    private Boolean unpaidInternships;

    private String recruitmentContactName;
    private String recruitmentContactEmail;
    private String recruitmentContactPhone;

}
