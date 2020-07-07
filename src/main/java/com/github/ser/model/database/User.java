package com.github.ser.model.database;

import lombok.*;
import org.hibernate.id.UUIDGenerationStrategy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Table(name="users")
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

    private LocalDateTime lastSeen;

    private Boolean shouldChangePassword;

}
