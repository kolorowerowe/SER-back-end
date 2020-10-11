package com.github.ser.model.database;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.github.ser.enums.Role;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.github.ser.util.DateTimeUtils.DATE_TIME_FORMAT;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@With
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")

    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NonNull
    private String email;

    private String password;

    private String fullName;

    private String phoneNumber;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime lastSeen;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime userCreatedDate;

    private Boolean isActivated;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyAccess> companyAccessList;

}
