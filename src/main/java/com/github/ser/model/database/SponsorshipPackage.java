package com.github.ser.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@With
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Table(name = "sponsorship_package")
@Entity
public class SponsorshipPackage {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private UUID id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<SponsorshipPackageTranslation> translations;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Price> prices;

    private Double standSize;

    private Boolean isAvailable;

    @JsonIgnore
    @OneToMany(mappedBy = "sponsorshipPackage",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Company> companies;
}
