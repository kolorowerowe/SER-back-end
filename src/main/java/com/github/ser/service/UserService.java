package com.github.ser.service;

import com.github.ser.model.database.User;
import com.github.ser.model.lists.UserListResponse;
import com.github.ser.model.requests.RegisterUserRequest;
import com.github.ser.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserListResponse getAllUsers() {
        List<User> users = userRepository.findAll();

        return UserListResponse.builder()
                .users(users)
                .count(users.size())
                .build();
    }

    public User getUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    public User registerUser(RegisterUserRequest registerUserRequest) {

        User newUser = User.builder()
                .email(registerUserRequest.getEmail())
                .fullName(registerUserRequest.getFullName())
                .phoneNumber(registerUserRequest.getPhoneNumber())
                .shouldChangePassword(registerUserRequest.getShouldChangePassword())
                .build();

        return userRepository.save(newUser);

    }
}
