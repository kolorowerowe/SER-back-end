package com.github.ser.testutils;

import com.github.ser.enums.Role;
import com.github.ser.model.database.User;
import com.github.ser.repository.UserRepository;

import java.util.UUID;

public class PopulateDatabase {

    public static UUID populateUserDatabase(UserRepository userRepository){
        userRepository.deleteAll();

        User user = User.builder()
                .uuid(UUID.fromString("00000000-0000-0000-0000-000000000000"))
                .email("dominik@ser.pl")
                .fullName("Dominik K")
                .isActivated(false)
                .role(Role.SYSTEM_ADMIN)
                .build();

        User savedUser = userRepository.save(user);

        return savedUser.getUuid();

    }
}
