package com.guildify.guildify.controller;

import com.guildify.guildify.model.dto.LoginRequest;
import com.guildify.guildify.model.dto.LoginResponse;
import com.guildify.guildify.model.dto.UserRequest;
import com.guildify.guildify.model.dto.UserResponse;
import com.guildify.guildify.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/auth/register")
    public UserResponse registerUser(@RequestBody UserRequest userRequest){
        return authenticationService.registerUser(userRequest);
    }
    @PostMapping("/auth/login")
    public LoginResponse loginUser(@RequestBody LoginRequest loginRequest){
        return authenticationService.loginUser(loginRequest);
    }

}