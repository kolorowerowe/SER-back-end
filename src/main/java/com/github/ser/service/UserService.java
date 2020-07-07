package com.github.ser.service;

import com.github.ser.exception.InvalidPasswordException;
import com.github.ser.exception.NoUserForEmailException;
import com.github.ser.model.database.User;
import com.github.ser.model.lists.UserListResponse;
import com.github.ser.model.requests.LoginUserRequest;
import com.github.ser.model.requests.RegisterUserRequest;
import com.github.ser.model.response.LoginUserResponse;
import com.github.ser.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
        return userRepository.findUserByEmail(email);
    }

    public User registerUser(RegisterUserRequest registerUserRequest) {

        User newUser = User.builder()
                .email(registerUserRequest.getEmail())
                .password(passwordEncoder.encode(registerUserRequest.getPassword()))
                .fullName(registerUserRequest.getFullName())
                .phoneNumber(registerUserRequest.getPhoneNumber())
                .shouldChangePassword(registerUserRequest.getShouldChangePassword())
                .build();
        return userRepository.save(newUser);
    }

    public LoginUserResponse loginUser(LoginUserRequest loginUserRequest) {
        User existingUser = userRepository.findUserByEmail(loginUserRequest.getEmail());

        if (existingUser == null){
            throw new NoUserForEmailException("No user for "+ loginUserRequest.getEmail() + " found");
        }

        if (!passwordEncoder.matches(loginUserRequest.getPassword(), existingUser.getPassword())){
            throw new InvalidPasswordException("Invalid password");
        }

        //TODO 07.07.2020: generate token
        return LoginUserResponse.builder()
                .authToken("YES")
                .build();
    }
}
