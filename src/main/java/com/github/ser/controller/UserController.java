package com.github.ser.controller;

import com.github.ser.model.database.User;
import com.github.ser.model.lists.UserListResponse;
import com.github.ser.model.requests.RegisterUserRequest;
import com.github.ser.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Log4j2
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/secured-all")
    public ResponseEntity<UserListResponse> getAllUsers2() {
        log.debug("Getting all users");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(params = {})
    public ResponseEntity<UserListResponse> getAllUsers() {
        log.debug("Getting all users");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(params = {"email"})
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        log.debug("Getting user by email: " + email);
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegisterUserRequest registerUserRequest) {
        log.info("Register new user: " + registerUserRequest.getFullName());
        return new ResponseEntity<>(userService.registerUser(registerUserRequest), HttpStatus.OK);
    }

}
