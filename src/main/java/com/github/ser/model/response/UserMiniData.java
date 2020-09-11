package com.github.ser.model.response;

import com.github.ser.model.database.User;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class UserMiniData {

    private UUID id;
    private String email;
    private String fullName;

    public UserMiniData(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
    }


}
