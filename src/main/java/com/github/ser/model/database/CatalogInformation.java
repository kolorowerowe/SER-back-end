package com.github.ser.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
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
}