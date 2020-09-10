package com.github.ser.controller;

import com.github.ser.model.database.User;
import com.github.ser.model.lists.UserListResponse;
import com.github.ser.model.requests.ChangeUserPasswordRequest;
import com.github.ser.model.requests.ChangeUserPersonalInfoRequest;
import com.github.ser.model.requests.RegisterUserRequest;
import com.github.ser.model.requests.SetUserPasswordRequest;
import com.github.ser.model.response.LoginUserResponse;
import com.github.ser.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@Log4j2
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(params = {})
    public ResponseEntity<UserListResponse> getAllUsers() {
        log.info("Getting all users");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(params = {"email"})
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        log.info("Getting user by email: " + email);
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/check-token")
    public ResponseEntity<Void> checkToken() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable UUID userId) {
        log.info("Getting user by id: " + userId);
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable UUID userId) {
        log.info("Deleting user by id: " + userId);
        userService.deleteUserById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping()
    public ResponseEntity<User> registerNewUser(@RequestBody RegisterUserRequest registerUserRequest) {
        log.info("Register new user: " + registerUserRequest.getFullName());
        return new ResponseEntity<>(userService.registerNewUser(registerUserRequest), HttpStatus.OK);
    }

    @GetMapping(value = "/verify/request", params = {"email"})
    public ResponseEntity<Void> sentVerificationCode(@RequestParam String email) {
        log.info("Started process of verification for user: " + email);
        userService.sentVerificationCode(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/verify/check", params = {"email", "code"})
    public ResponseEntity<LoginUserResponse> verifyCode(@RequestParam String email, @RequestParam String code) {
        log.info("Verifying user by email: " + email + " and code: " + code);
        LoginUserResponse loginUserResponse = userService.verifyCode(email, code);
        return new ResponseEntity<>(loginUserResponse, HttpStatus.OK);
    }

    @PostMapping("/{userId}/password/set")
    public ResponseEntity<User> setUserPassword(@PathVariable UUID userId, @RequestBody SetUserPasswordRequest setUserPasswordRequest){
        log.info("Setting password for user: " + userId);
        return new ResponseEntity<>(userService.setUserPassword(userId, setUserPasswordRequest), HttpStatus.OK);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<User> changeUserPersonalInfo(@PathVariable UUID userId, @RequestBody ChangeUserPersonalInfoRequest changeUserPersonalInfoRequest){
        log.info("Changing personal info for user: " + userId);
        return new ResponseEntity<>(userService.changeUserPersonalInfo(userId, changeUserPersonalInfoRequest), HttpStatus.OK);

    }

    @PostMapping("/{userId}/password")
    public ResponseEntity<User> changeUserPassword(@PathVariable UUID userId, @RequestBody ChangeUserPasswordRequest changeUserPasswordRequest){
        log.info("Changing password for user: " + userId);
        return new ResponseEntity<>(userService.changeUserPassword(userId, changeUserPasswordRequest), HttpStatus.OK);
    }

}
