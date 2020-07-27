package com.github.ser.model.database;

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
@Table(name = "company")
@Entity
public class Company {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private UUID uuid;

    private UUID primaryUserId;

    private String name;

    private String contactPhone;

    private String taxId;

}
