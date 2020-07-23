package com.github.ser.service;

import com.github.ser.SerApplication;
import com.github.ser.exception.badRequest.NoUserForEmailException;
import com.github.ser.exception.badRequest.NoUserForUuidException;
import com.github.ser.model.database.User;
import com.github.ser.repository.UserRepository;
import com.github.ser.util.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static com.github.ser.testutils.PopulateDatabase.populateUserDatabase;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("User Service tests")
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mock
    private VerificationCodeService verificationCodeService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private EmailService emailService;


    private UserService userService;

    private UUID userUuid;

    @BeforeEach
    void setup() {
        userUuid = populateUserDatabase(userRepository);

        this.userService = new UserService(userRepository, passwordEncoder, verificationCodeService, jwtTokenUtil, emailService);
    }

    @Test
    @DisplayName("Get user by id - return NoUserForUuidException")
    void getUserById_throwNoUserUuidException() {

        UUID notExistingUserUuid = UUID.fromString("00000000-9999-9999-9999-000000000000");

        assertThrows(NoUserForUuidException.class, () -> userService.getUserById(notExistingUserUuid));
    }

    @Test
    @DisplayName("Get user by email - return NoUserForEmailException")
    void getUserByEmail_throwNoUserEmailException() {

        String notExistingEmail = "xd@xd.xd";

        assertThrows(NoUserForEmailException.class, () -> userService.getUserByEmail(notExistingEmail));
    }

    @Test
    @DisplayName("Get user by id - return User")
    void getUserById_returnUser() {


        User user = userService.getUserById(userUuid);

        assertAll(
                () -> assertNotNull(user),
                () -> assertEquals("Dominik K", user.getFullName())
        );
    }

    @Test
    @DisplayName("Get user by email - return User")
    void getUserByEmail_returnUser() {


        User user = userService.getUserByEmail("dominik@ser.pl");

        assertAll(
                () -> assertNotNull(user),
                () -> assertEquals("Dominik K", user.getFullName())
        );
    }
}