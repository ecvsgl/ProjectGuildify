package com.guildify.guildify.service;

import com.guildify.guildify.model.GameCharEntity;
import com.guildify.guildify.model.UserEntity;
import com.guildify.guildify.model.dto.GameCharResponse;
import com.guildify.guildify.model.dto.UserRequest;
import com.guildify.guildify.model.dto.UserResponse;
import com.guildify.guildify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;

    //UserCreation has moved to "Authservice" due to SpringSecurity logic.

    //Search for a UserEntity in DB
    public UserResponse getSpecificUserEntity(String jwt, String userDisplayName){
        return userEntityToUserResponseMapper(jwt, userRepository.findUserEntityByDisplayName(userDisplayName));
    }
    //Get all Users in DB
    public List<UserResponse> getAllUserEntities(String jwt){
        List<UserEntity> allUserEntities = userRepository.findAll();
        List<UserResponse> allUserEntitiesAsUserResponses = new ArrayList<>();
        for(UserEntity x: allUserEntities){
            UserResponse userResponse = userEntityToUserResponseMapper(jwt,x);
            allUserEntitiesAsUserResponses.add(userResponse);
        }
        return allUserEntitiesAsUserResponses;
    }
    //Delete a User in DB
    public String deleteUserEntity(String bearerToken, Integer userId){
        if (userRepository.existsById(userId)){
            userRepository.delete(userRepository.findUserEntityByUserId(userId));
            return "User deleted successfully.";
        }
        return "No such user with this ID exists.";
    }
    //Update PW of a User, do not allow any more updates.
    public String updateUserEntityPassword(String jwt, UserRequest userRequest){
        UserEntity userEntity = jwtUserEntityExtractor(jwt);
        if(!Objects.equals(userEntity.getUsername(), userRequest.getUsername())){
            return "You cannot change password of another user.";
        }
        userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userRepository.save(userEntity);
        return "Password changed successfully.";
    }

    private UserResponse userEntityToUserResponseMapper(String jwt, UserEntity userEntity){
        UserResponse userResponse = UserResponse.builder()
                .userId(userEntity.getUserId())
                .displayName(userEntity.getDisplayName())
                .email(userEntity.getEmail())
                .accountRank(userEntity.getAccountRank())
                .gameCharResponseList(null)
                .build();
        List<GameCharResponse> gameCharResponseList = new ArrayList<>();
        for(GameCharEntity x: userEntity.getGameCharEntityList()){
            GameCharResponse gcr = GameCharResponse.builder()
                    .charId(x.getCharId())
                    .charName(x.getCharName())
                    .charLevel(x.getCharLevel())
                    .charOwner(x.getUserEntity().getDisplayName())
                    .charGameName(x.getGameEntity().getGameName())
                    .charGuildName(x.getGuildEntity().getGuildName())
                    .build();
            gcr.setCreatedAt(LocalDateTime.now());
            gcr.setCreatedBy(jwtUserEntityExtractor(jwt).getDisplayName());
            gameCharResponseList.add(gcr);
        }
        userResponse.setGameCharResponseList(gameCharResponseList);
        return userResponse;
    }

    public UserEntity jwtUserEntityExtractor(String jwt){
        Map<String, Object> claims = tokenService.decodeJwt(jwt).getClaims();
        return userRepository.findUserEntityByUsername((String)claims.get("sub"));
    }
}
