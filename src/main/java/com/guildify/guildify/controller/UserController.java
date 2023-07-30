package com.guildify.guildify.controller;


import com.guildify.guildify.model.UserEntity;
import com.guildify.guildify.model.dto.UserRequest;
import com.guildify.guildify.model.dto.UserResponse;
import com.guildify.guildify.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/newuser")
    public UserResponse createNewUserEntity(@RequestBody UserRequest userRequest){
        return userService.createNewUserEntity(userRequest);
    }
    @GetMapping("/userSearch/{userId}")
    public UserEntity getSpecificUserEntity(@PathVariable int userId){
        return userService.getSpecificUserEntity(userId);
    }
    @GetMapping("/userlist")
    public List<UserEntity> getAllUserEntities(){
        return userService.getAllUserEntities();
    }
    @DeleteMapping("/rmuser/{userId}")
    public String deleteUserEntity(@PathVariable Integer userId){
        userService.deleteUserEntity(userId);
        return "User Deleted Successfully.";
    }
    @PutMapping("/userpwchange/{userId}")
    public String updateUserEntityPassword(@PathVariable Integer userId,
                                           @RequestBody String newPassword){
        userService.updateUserEntityPassword(userId,newPassword);
        return "Password Changed Successfully.";
    }
}
