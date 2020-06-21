package com.github.ser.controller;

import com.github.ser.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/greeting")
    public ResponseEntity<String> getGreeting(){
            return new ResponseEntity<>(userService.getGreeting(), HttpStatus.OK);
    }
}
