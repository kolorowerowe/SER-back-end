package com.github.ser.model.database;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@With
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Table(name = "sp_equipment")
@Entity
public class SPEquipment {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private UUID id;

    private UUID sponsorshipPackageId;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    private Integer count;
}
