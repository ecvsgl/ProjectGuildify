package com.guildify.guildify.service;

import com.guildify.guildify.model.UserEntity;
import com.guildify.guildify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;



    public UserEntity getSpecificUserEntity(Integer userId){

        return userRepository.findUserByUserId(userId);
    }

    public void deleteUserEntity(Integer userId){
        userRepository.delete(userRepository.findUserByUserId(userId));
    }

    public void updateUserEntityPassword(Integer userId, String newPassword){
        UserEntity userEntity = userRepository.findUserByUserId(userId);
        userEntity.setPasswordHash(stringHashingMethod(newPassword));
    }

    public static String stringHashingMethod(String inputString){
        StringBuilder sb = new StringBuilder();
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] hashes = messageDigest.digest(inputString.getBytes());
            for (byte a: hashes){
                sb.append(String.format("%02X",a));
            }
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("deletemepls");
        }
        return sb.toString();
    }
}
