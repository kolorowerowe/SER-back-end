package com.github.ser.testutils;

import com.github.ser.enums.Role;
import com.github.ser.model.database.User;
import com.github.ser.model.database.VerificationCode;
import com.github.ser.repository.UserRepository;
import com.github.ser.repository.VerificationCodeRepository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PopulateDatabase {

    public static List<UUID> populateUserDatabase(UserRepository userRepository) {
        userRepository.deleteAll();

        User user1 = User.builder()
                .uuid(UUID.fromString("00000000-0000-0000-0000-000000000000"))
                .email("dominik@ser.pl")
                .fullName("Dominik K")
                .isActivated(true)
                .role(Role.SYSTEM_ADMIN)
                .build();

        User user2 = User.builder()
                .uuid(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .email("rekin@ser.pl")
                .fullName("Rekin rekin")
                .isActivated(false)
                .role(Role.COMPANY_EDITOR)
                .build();

        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);

        return Arrays.asList(savedUser1.getUuid(), savedUser2.getUuid());

    }

    public static void populateVerificationCodeRepository(VerificationCodeRepository verificationCodeRepository) {
        verificationCodeRepository.deleteAll();

        VerificationCode verificationCode = VerificationCode.builder()
                .userEmail("rekin@ser.pl")
                .code("112233")
                .build();

        verificationCodeRepository.save(verificationCode);

    }
}
