package com.github.ser.service;

import com.github.ser.model.database.User;
import com.github.ser.model.lists.UserListResponse;
import com.github.ser.model.requests.RegisterUserRequest;
import com.github.ser.repository.UserRepository;
import com.github.ser.util.JwtTokenUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenUtil jwtTokenUtil) {
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
        return userRepository.findUserByEmail(email);
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
}
