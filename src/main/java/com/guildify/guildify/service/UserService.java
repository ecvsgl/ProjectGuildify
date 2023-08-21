package com.guildify.guildify.service;

import com.guildify.guildify.model.UserEntity;
import com.guildify.guildify.model.dto.UserRequest;
import com.guildify.guildify.model.dto.UserResponse;
import com.guildify.guildify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    //Search for a UserEntity in DB
    public UserEntity getSpecificUserEntity(int userId){
        return userRepository.findUserEntityByUserId(userId);
    }
    //Get all Users in DB
    public List<UserEntity> getAllUserEntities(){
        return userRepository.findAll();
    }
    //Delete a User in DB
    public void deleteUserEntity(Integer userId){
        userRepository.delete(userRepository.findUserEntityByUserId(userId));
    }
    //Update PW of a User, do not allow any more updates.
    public void updateUserEntityPassword(Integer userId, String newPassword){
        UserEntity userEntity = userRepository.findUserEntityByUserId(userId);
        userEntity.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(userEntity);
    }

}
