package com.github.ser.service;

import com.github.ser.SerApplication;
import com.github.ser.enums.Role;
import com.github.ser.exception.auth.InvalidVerificationCodeException;
import com.github.ser.exception.badRequest.NoUserForEmailException;
import com.github.ser.exception.badRequest.NoUserForUuidException;
import com.github.ser.model.database.User;
import com.github.ser.model.lists.UserListResponse;
import com.github.ser.model.requests.RegisterUserRequest;
import com.github.ser.model.response.LoginUserResponse;
import com.github.ser.repository.UserRepository;
import com.github.ser.repository.VerificationCodeRepository;
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
import static com.github.ser.testutils.PopulateDatabase.populateVerificationCodeRepository;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("User Service tests")
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mock
    private VerificationCodeService verificationCodeService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private EmailService emailService;


    private UserService userService;

    private User firstUser;

    @BeforeEach
    void setup() {
        firstUser = populateUserDatabase(userRepository).get(0);
        populateVerificationCodeRepository(verificationCodeRepository);

        verificationCodeService = new VerificationCodeService(verificationCodeRepository);
        userService = new UserService(userRepository, passwordEncoder, verificationCodeService, jwtTokenUtil, emailService);
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


        User user = userService.getUserById(firstUser.getId());

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

    @Test
    @DisplayName("Get all users - return UserListResponse")
    void getAllUsers_returnUserListResponse() {


        UserListResponse userListResponse = userService.getAllUsers();

        assertAll(
                () -> assertNotNull(userListResponse),
                () -> assertEquals(3, userListResponse.getCount()),
                () -> assertEquals("Dominik K", userListResponse.getUsers().get(0).getFullName())
        );
    }

    @Test
    @DisplayName("Delete user by id")
    void deleteUserById() {

        assertDoesNotThrow(() -> userService.getUserById(firstUser.getId()));

        userService.deleteUserById(firstUser.getId());

        assertThrows(NoUserForUuidException.class, () -> userService.getUserById(firstUser.getId()));

    }

    @Test
    @DisplayName("Verify cose - throws InvalidVerificationCodeException")
    void verifyCode_throwsException() {

        String email = "dominik@ser.pl";
        String wrongCode = "998877";

        assertThrows(InvalidVerificationCodeException.class, () -> userService.verifyCode(email, wrongCode));
    }

    @Test
    @DisplayName("Verify code - return LoginUserResponse")
    void verifyCode_returnLoginUserResponse() {

        String email = "rekin@ser.pl";
        String correctCode = "112233";

        LoginUserResponse loginUserResponse = userService.verifyCode(email, correctCode);

        assertAll(
                () -> assertNotNull(loginUserResponse.getUser()),
                () -> assertEquals("Rekin rekin", loginUserResponse.getUser().getFullName()),
                () -> assertNotNull(loginUserResponse.getAuthToken())
        );
    }


    @Test
    @DisplayName("Register new user - add user to db")
    void registerNewUser_addNewUser() {

        assertThrows(NoUserForEmailException.class, () -> userService.getUserByEmail("nowy@ser.pl"));
        RegisterUserRequest registerUserRequest = RegisterUserRequest.builder()
                .email("nowy@ser.pl")
                .fullName("I'm new here")
                .phoneNumber("+8989")
                .role(Role.ORGANIZER_VIEWER)
                .build();


        User user = userService.registerNewUser(registerUserRequest);

        assertAll(
                () -> assertDoesNotThrow(() -> userService.getUserByEmail("nowy@ser.pl")),
                () -> assertEquals("+8989", user.getPhoneNumber())
        );


    }
}