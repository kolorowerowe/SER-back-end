package com.github.ser.model.database;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.github.ser.enums.Role;
import com.github.ser.util.DateTimeUtils;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.github.ser.util.DateTimeUtils.*;

@Data
@With
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Table(name = "users")
@Entity
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private UUID uuid;

    @NonNull
    private String email;

    private String password;

    private String fullName;

    private String phoneNumber;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime lastSeen;


    private Boolean isEnabled;

    private Boolean shouldChangePassword;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Role role;

}
