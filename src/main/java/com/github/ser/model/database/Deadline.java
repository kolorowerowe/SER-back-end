package com.github.ser.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.ser.enums.DeadlineActivity;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Builder
@Data
@Entity
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Table
public class Deadline {

    @Id
    @Column(name = "id")
    @GeneratedValue
    @JsonIgnore
    private UUID id;

    private Integer orderNumber;

    @Enumerated(EnumType.STRING)
    private DeadlineActivity activity;

    private String deadlineDate;

}