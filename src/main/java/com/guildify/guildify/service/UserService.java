package com.guildify.guildify.service;

import com.guildify.guildify.model.UserEntity;
import com.guildify.guildify.model.dto.UserRequest;
import com.guildify.guildify.model.dto.UserResponse;
import com.guildify.guildify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    //Create a New User
    public UserResponse createNewUserEntity(UserRequest userRequest){
        //Request to Entity Mapping
        if(!checkForUsernameAvailability(userRequest.getUsername())){
            throw new IllegalArgumentException("Please provide another username.");
        }
        if(!checkForDisplayNameAvailability(userRequest.getDisplayName())){
            throw new IllegalArgumentException("Please provide another display name.");
        }
        UserEntity userEntity = UserEntity.builder()
                .usernameHash(stringHashingMethod(userRequest.getUsername()))
                .passwordHash(stringHashingMethod(userRequest.getPassword()))
                .displayName(userRequest.getDisplayName())
                .email(userRequest.getEmail())
                .accountRank("Noob")
                .accountCreationDate(LocalDateTime.now())
                .build();
        userEntity = userRepository.save(userEntity);
        //Response Building...
        UserResponse userResponse = UserResponse.builder()
                .accountCreationDate(userEntity.getAccountCreationDate())
                .accountRank(userEntity.getAccountRank())
                .displayName(userEntity.getDisplayName())
                .build();
        return userResponse;
    }
    //Search for a UserEntity in DB
    public UserEntity getSpecificUserEntity(Integer userId){
        return userRepository.findUserByUserId(userId);
    }
    //Get all Users in DB
    public List<UserEntity> getAllUserEntities(){
        return userRepository.findAll();
    }
    //Delete a User in DB
    public void deleteUserEntity(Integer userId){
        userRepository.delete(userRepository.findUserByUserId(userId));
    }
    //Update PW of a User, do not allow any more updates.
    public void updateUserEntityPassword(Integer userId, String newPassword){
        UserEntity userEntity = userRepository.findUserByUserId(userId);
        userEntity.setPasswordHash(stringHashingMethod(newPassword));
    }
    public boolean checkForUsernameAvailability(String username){
        UserEntity userEntity = userRepository.findUserEntityByUsernameHash(stringHashingMethod(username));
        return userEntity != null;
    }
    public boolean checkForDisplayNameAvailability(String displayName){
        UserEntity userEntity = userRepository.findUserEntityByDisplayName(displayName);
        return userEntity != null;
    }
    public String stringHashingMethod(String inputString){
        StringBuilder sb = new StringBuilder();
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] hashes = messageDigest.digest(inputString.getBytes());
            for (byte a: hashes){
                sb.append(String.format("%02X",a));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }
}
