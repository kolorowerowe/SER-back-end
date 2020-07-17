package com.github.ser.service;

import com.github.ser.exception.auth.InvalidOldPasswordException;
import com.github.ser.exception.auth.InvalidRepeatPasswordException;
import com.github.ser.exception.auth.NoUserForEmailException;
import com.github.ser.model.database.User;
import com.github.ser.model.lists.UserListResponse;
import com.github.ser.model.requests.ChangeUserPasswordRequest;
import com.github.ser.model.requests.RegisterUserRequest;
import com.github.ser.repository.UserRepository;
import com.github.ser.util.JwtTokenUtil;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Hibernate;
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

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserListResponse getAllUsers() {
        List<User> users = userRepository.findAll();

        return UserListResponse.builder()
                .users(users)
                .count(users.size())
                .build();
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            log.debug("User: " + email + " does not exist");
            throw new NoUserForEmailException("No user for provided email");
        }
        return user;
    }

    public User registerUser(RegisterUserRequest registerUserRequest) {

        User newUser = User.builder()
                .email(registerUserRequest.getEmail())
                .password(passwordEncoder.encode(registerUserRequest.getPassword()))
                .fullName(registerUserRequest.getFullName())
                .phoneNumber(registerUserRequest.getPhoneNumber())
                .shouldChangePassword(registerUserRequest.getShouldChangePassword())
                .role(registerUserRequest.getRole())
                .build();
        return userRepository.save(newUser);
    }

    public void setLastSeenNow(User user){
        user.setLastSeen(LocalDateTime.now());
        userRepository.save(user);
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

        User savedUser =  (User) Hibernate.unproxy(userRepository.save(updatedUser));
        return savedUser;
    }
}
