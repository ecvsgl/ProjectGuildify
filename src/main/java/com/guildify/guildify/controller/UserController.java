package com.guildify.guildify.controller;


import com.guildify.guildify.model.dto.UserRequest;
import com.guildify.guildify.model.dto.UserResponse;
import com.guildify.guildify.service.UserService;
import com.guildify.guildify.utility.StaticMethods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/admin/usersearch/{userDisplayName}")
    public UserResponse getSpecificUserEntity(@RequestHeader("Authorization") String bearerToken,
                                              @PathVariable String userDisplayName){
        return userService.getSpecificUserEntity(StaticMethods.getJwtFromRequestHeader(bearerToken), userDisplayName);
    }
    @GetMapping("/admin/allusers")
    public List<UserResponse> getAllUserEntities(@RequestHeader("Authorization") String bearerToken){
        return userService.getAllUserEntities(StaticMethods.getJwtFromRequestHeader(bearerToken));
    }
    @DeleteMapping("/admin/deleteuser/{userId}")
    public String deleteUserEntity(@RequestHeader("Authorization") String bearerToken,
                                            @PathVariable Integer userId){
        return userService.deleteUserEntity(StaticMethods.getJwtFromRequestHeader(bearerToken), userId);
    }
    @PutMapping("/user/userpasswordchange")
    public String updateUserEntityPassword(@RequestHeader("Authorization") String bearerToken,
                                           @RequestBody UserRequest userRequest){
        return userService.updateUserEntityPassword(StaticMethods.getJwtFromRequestHeader(bearerToken),userRequest);

    }
}
