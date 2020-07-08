package com.github.ser.model.database;

import com.github.ser.enums.Role;
import lombok.*;
import org.hibernate.id.UUIDGenerationStrategy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    private Boolean isEnabled;

    private Boolean shouldChangePassword;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Role role;

}
