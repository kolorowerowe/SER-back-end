package com.github.ser.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class CatalogInformation {

    @Id
    @Column(name = "id")
    @GeneratedValue
    @JsonIgnore
    private UUID id;

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


    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;


    public static List<String> getHeaders() {
        return Arrays.asList(
                "ID",
                "Company ID",
                "Company Name",
                "Description",
                "Email",
                "Website",
                "Business Profile",
                "Number of employees Poland",
                "Number of employees Worldwide",
                "Candidate Requirements",
                "Main office location",
                "Subsidiary offices locations",
                "Number of job vacancies",
                "Paid internships",
                "Unpaid internships",
                "Recruitment Contact Name",
                "Recruitment Contact Email",
                "Recruitment Contact Phone"
        );
    }

    public List<String> getRow() {
        return Arrays.asList(
                id.toString(),
                company.getId().toString(),
                companyName,
                description,
                email,
                website,
                businessProfile,
                numberOfEmployeesPoland.toString(),
                numberOfEmployeesWorldwide.toString(),
                candidateRequirements,
                mainOfficeLocation,
                subsidiaryOfficesLocations,
                numberOfJobVacancies.toString(),
                paidInternships.toString(),
                unpaidInternships.toString(),
                recruitmentContactName,
                recruitmentContactEmail,
                recruitmentContactPhone
        );
    }
}