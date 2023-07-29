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
    @GetMapping("/userSearch")
    public UserEntity getSpecificUserEntity(@RequestBody Integer userId){
        return userService.getSpecificUserEntity(userId);
    }
    @GetMapping("/userlist")
    public List<UserEntity> getAllUserEntities(){
        return userService.getAllUserEntities();
    }
    @DeleteMapping
    public void deleteUserEntity(@RequestBody Integer userId){
        userService.deleteUserEntity(userId);
    }
    @PutMapping("/userpwchange")
    public String updateUserEntityPassword(@RequestBody Integer userId,
                                           @RequestBody String newPassword){
        userService.updateUserEntityPassword(userId,newPassword);
        return "Password Changed Successfully.";
    }

}
