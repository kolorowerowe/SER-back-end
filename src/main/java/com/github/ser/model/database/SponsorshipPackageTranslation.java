package com.github.ser.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Table(name = "sponsorship_package_translation")
@Entity
public class SponsorshipPackageTranslation {

    @Id
    @Column(name = "id")
    @GeneratedValue
    @JsonIgnore
    private UUID id;

    private String languageCode;

    private String name;

    private String description;

}
