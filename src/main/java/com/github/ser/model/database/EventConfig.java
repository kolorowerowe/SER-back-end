package com.github.ser.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Table
public class EventConfig {

    @Id
    @Column(name = "id")
    @GeneratedValue
    @JsonIgnore
    private UUID id;

    private String eventDate;

}