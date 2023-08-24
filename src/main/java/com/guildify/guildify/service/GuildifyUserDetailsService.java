package com.guildify.guildify.service;

import com.guildify.guildify.model.UserEntity;
import com.guildify.guildify.model.dto.UserResponse;
import com.guildify.guildify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuildifyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity= userRepository.findUserEntityByUsername(username);
        if(userEntity==null){
            throw new UsernameNotFoundException("Such username does not exists.");
        }
        return userEntity;
    }
}
