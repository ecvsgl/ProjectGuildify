package com.guildify.guildify.service;

import com.guildify.guildify.model.Role;
import com.guildify.guildify.model.UserEntity;
import com.guildify.guildify.model.dto.LoginRequest;
import com.guildify.guildify.model.dto.LoginResponse;
import com.guildify.guildify.model.dto.UserRequest;
import com.guildify.guildify.model.dto.UserResponse;
import com.guildify.guildify.repository.RoleRepository;
import com.guildify.guildify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;


    public UserResponse registerUser(UserRequest userRequest){
        if(userRepository.findUserEntityByUsername(userRequest.getUsername())!=null){
            throw new IllegalArgumentException("Please provide another username.");
        }
        if(userRepository.findUserEntityByDisplayName(userRequest.getDisplayName())!=null){
            throw new IllegalArgumentException("Please provide another displayname.");
        }
        Set<Role> userAuthorities = new HashSet<>();
        userAuthorities.add(roleRepository.findRoleByAuthority("STANDARD_USER"));
        //Request to Entity mapping.
        UserEntity userEntity = UserEntity.builder()
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .displayName(userRequest.getDisplayName())
                .email(userRequest.getEmail())
                .accountRank("Noobie")
                .postEntityList(null)
                .postCommentsEntityList(null)
                .gameCharEntityList(null)
                .authorities(userAuthorities)
                .build();
        userEntity = userRepository.save(userEntity);
        //Entity to Response mapping...
        return userEntityToResponseMapper(userEntity);
    }

    public LoginResponse loginUser(LoginRequest loginRequest){
        try{
            Authentication auth =authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword())
            );
            String token =tokenService.generateJwt(auth);
            UserEntity userEntity = userRepository.findUserEntityByUsername(loginRequest.getUsername());
            LoginResponse loginResponse = new LoginResponse(userEntityToResponseMapper(userEntity),token);
            return loginResponse;
        } catch (Exception e){
            return new LoginResponse(null,"");
        }
    }

    private UserResponse userEntityToResponseMapper(UserEntity userEntity){
        UserResponse userResponse = UserResponse.builder()
                .userId(userEntity.getUserId())
                .displayName(userEntity.getDisplayName())
                .email(userEntity.getEmail())
                .accountRank(userEntity.getAccountRank())
                .gameCharResponseList(null)
                .build();
        return userResponse;
    }
}
