package com.mua.ibbeauty.controller;

import com.mua.ibbeauty.model.DTO.UserRegistrationDTO;
import com.mua.ibbeauty.model.User;
import com.mua.ibbeauty.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO){
        User user = userService.registerUser(userRegistrationDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
