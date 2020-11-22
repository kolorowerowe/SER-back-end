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
public class Address {

    @Id
    @Column(name = "id")
    @GeneratedValue
    @JsonIgnore
    private UUID id;

    private String street;
    private String buildingNumber;
    private String flatNumber;
    private String city;
    private String postalCode;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;
}