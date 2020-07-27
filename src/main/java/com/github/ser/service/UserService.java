package com.github.ser.service;

import com.github.ser.exception.auth.InvalidVerificationCodeException;
import com.github.ser.exception.badRequest.InvalidOldPasswordException;
import com.github.ser.exception.badRequest.InvalidRepeatPasswordException;
import com.github.ser.exception.badRequest.NoUserForEmailException;
import com.github.ser.exception.badRequest.NoUserForUuidException;
import com.github.ser.model.database.CompanyAccess;
import com.github.ser.model.database.User;
import com.github.ser.model.lists.UserListResponse;
import com.github.ser.model.requests.*;
import com.github.ser.model.response.LoginUserResponse;
import com.github.ser.repository.UserRepository;
import com.github.ser.util.JwtTokenUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationCodeService verificationCodeService;
    private final JwtTokenUtil jwtTokenUtil;
    private final EmailService emailService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       VerificationCodeService verificationCodeService,
                       JwtTokenUtil jwtTokenUtil,
                       EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.verificationCodeService = verificationCodeService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.emailService = emailService;
    }

    public UserListResponse getAllUsers() {
        List<User> users = userRepository.findAll();

        return UserListResponse.builder()
                .users(users)
                .count(users.size())
                .build();
    }

    public User getUserById(UUID userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            log.debug("User: " + userId + " does not exist");
            throw new NoUserForUuidException("No user for provided email");
        }
        return user;
    }

    public void deleteUserById(UUID userId) {
        userRepository.deleteById(userId);
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            log.debug("User: " + email + " does not exist");
            throw new NoUserForEmailException("No user for provided email");
        }
        return user;
    }

    public void sentVerificationCode(String email) {

        String code = verificationCodeService.generateCode(email);

        MailMessage verificationMessage = MailMessage.getVerificationMailMessage(email, code);
        emailService.sendMessage(verificationMessage);
    }

    public LoginUserResponse verifyCode(String email, String code) {
        Boolean isVerified = verificationCodeService.verifyCode(email, code);
        if (!isVerified) {
            throw new InvalidVerificationCodeException("Verification code error");
        }

        User user = getUserByEmail(email);

        String authToken = jwtTokenUtil.generateTokenForUser(user, true);

        return LoginUserResponse.builder()
                .authToken(authToken)
                .user(user)
                .build();

    }

    public User registerNewUser(RegisterUserRequest registerUserRequest) {

        User newUser = User.builder()
                .email(registerUserRequest.getEmail())
                .fullName(registerUserRequest.getFullName())
                .phoneNumber(registerUserRequest.getPhoneNumber())
                .role(registerUserRequest.getRole())
                .userCreatedDate(LocalDateTime.now())
                .isActivated(false)
                .build();
        return userRepository.save(newUser);
    }

    public void setLastSeenNow(User user) {
        user.setLastSeen(LocalDateTime.now());
        userRepository.save(user);
    }

    public User changeUserPersonalInfo(UUID userId, ChangeUserPersonalInfoRequest changeUserPersonalInfoRequest) {
        User user = userRepository.getOne(userId);

        String newFullName = changeUserPersonalInfoRequest.getFullName();
        if (newFullName != null && !newFullName.isEmpty()) {
            user = user.withFullName(newFullName);
        }

        String newPhoneNumber = changeUserPersonalInfoRequest.getPhoneNumber();
        if (newPhoneNumber != null && !newPhoneNumber.isEmpty()) {
            user = user.withPhoneNumber(newPhoneNumber);
        }

        return userRepository.save(user);
    }

    public User setUserPassword(UUID userId, SetUserPasswordRequest setUserPasswordRequest) {
        User user = userRepository.getOne(userId);

        if (!setUserPasswordRequest.getNewPassword().equals(setUserPasswordRequest.getRepeatNewPassword())) {
            throw new InvalidRepeatPasswordException("Password and repeat password does not match");
        }

        User updatedUser = user.withPassword(passwordEncoder.encode(setUserPasswordRequest.getNewPassword()))
                .withIsActivated(true);

        return userRepository.save(updatedUser);

    }

    public User changeUserPassword(UUID userId, ChangeUserPasswordRequest changeUserPasswordRequest) {
        User user = userRepository.getOne(userId);

        if (!passwordEncoder.matches(changeUserPasswordRequest.getOldPassword(), user.getPassword())) {
            throw new InvalidOldPasswordException("Invalid old password");
        }

        if (!changeUserPasswordRequest.getNewPassword().equals(changeUserPasswordRequest.getRepeatNewPassword())) {
            throw new InvalidRepeatPasswordException("Password and repeat password does not match");
        }

        User updatedUser = user.withPassword(passwordEncoder.encode(changeUserPasswordRequest.getNewPassword()));

        return userRepository.save(updatedUser);

    }

    public User addCompanyAccess(UUID userId, UUID companyId){
        User user = getUserById(userId);

        CompanyAccess newCompanyAccess = CompanyAccess.builder()
                .companyUUID(companyId)
                .user(user)
                .build();

        user.getCompanyAccessList().add(newCompanyAccess);
        return userRepository.save(user);
    }
}
