package com.guildify.guildify.service;

import com.guildify.guildify.model.GameCharEntity;
import com.guildify.guildify.model.UserEntity;
import com.guildify.guildify.model.dto.GameCharGuildJoinRequest;
import com.guildify.guildify.model.dto.GameCharRequest;
import com.guildify.guildify.model.dto.GameCharResponse;
import com.guildify.guildify.repository.GameCharRepository;
import com.guildify.guildify.repository.GameRepository;
import com.guildify.guildify.repository.GuildRepository;
import com.guildify.guildify.repository.UserRepository;
import com.guildify.guildify.utility.StaticMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.guildify.guildify.utility.StaticMethods.isNumeric;

@Service
public class GameCharService {

    @Autowired
    private GameCharRepository gameCharRepository;
    @Autowired
    private GuildRepository guildRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private TokenService tokenService;

    public List<GameCharResponse> getAllGameChars(String jwt){
        List<GameCharResponse> responseList = new ArrayList<>();
        for(GameCharEntity x: gameCharRepository.findAll()){
            responseList.add(gameCharEntityToResponseMapper(jwt,x));
        }
        return responseList;
    }

    public GameCharResponse addNewGameChar(String jwt, GameCharRequest gameCharRequest) {
        if (gameCharRequest.getCharName().length() < 1 || isNumeric(gameCharRequest.getCharName())) {
            throw new IllegalArgumentException("The Character name cannot be only numeric or lesser than 1 characters");
        }
        if(jwtUserEntityExtractor(jwt)!=userRepository.findUserEntityByDisplayName(gameCharRequest.getUserDisplayName())){
            throw new IllegalArgumentException("You cannot create a character for another user.");
        }
        if(gameRepository.findGameEntityByGameName(gameCharRequest.getGameName())==null){
            throw new IllegalArgumentException("You cannot create a character for a game that does not exist.");
        }
        if(!StaticMethods.isNumeric(gameCharRequest.getCharLevel())){
            throw new IllegalArgumentException("Level must be in numeric.");
        }
        // DTO to Entity mapper
        GameCharEntity gameCharEntity = GameCharEntity.builder()
                .charName(gameCharRequest.getCharName())
                .charLevel(gameCharRequest.getCharLevel())
                .userEntity(userRepository.findUserEntityByDisplayName(gameCharRequest.getUserDisplayName()))
                .gameEntity(gameRepository.findGameEntityByGameName(gameCharRequest.getGameName()))
                .build();
        gameCharEntity.setTimestamp(LocalDateTime.now());
        gameCharEntity.setCreatedBy(jwtUserEntityExtractor(jwt).getDisplayName());
        gameCharEntity = gameCharRepository.save(gameCharEntity);
        return gameCharEntityToResponseMapper(jwt,gameCharEntity);
    }

    public List<GameCharResponse> getMyAllGameChars(String jwt){
        List<GameCharEntity> myChars = jwtUserEntityExtractor(jwt).getGameCharEntityList();
        List<GameCharResponse> myAllGameChars = new ArrayList<>();
        for(GameCharEntity x: myChars){
            myAllGameChars.add(gameCharEntityToResponseMapper(jwt,x));
        }
        return myAllGameChars;
    }

    public GameCharResponse updateGameChar(String jwt, int charId, String newCharName) {
        GameCharEntity foundCharacter = gameCharRepository.findGameCharEntityByCharId(charId);
        if(foundCharacter==null){
            throw new IllegalArgumentException("There is no such ID. CharId = " + charId);
        } else if(foundCharacter.getUserEntity() != jwtUserEntityExtractor(jwt)){
            throw new IllegalArgumentException("You cannot make changes in other users characters.");
        }
        foundCharacter.setCharName(newCharName);
        foundCharacter = gameCharRepository.save(foundCharacter);
        return gameCharEntityToResponseMapper(jwt,foundCharacter);
    }

    public GameCharResponse getGameCharById(String jwt, int charId) {
        if(gameCharRepository.findGameCharEntityByCharId(charId) == null){
            throw new IllegalArgumentException("There is no such ID. CharId = " + charId);
        }
        if(jwtUserEntityExtractor(jwt)!=gameCharRepository.findGameCharEntityByCharId(charId).getUserEntity()){
            throw new IllegalArgumentException("You cannot operate on other people's characters.");

        }
        return gameCharEntityToResponseMapper(jwt,gameCharRepository.findGameCharEntityByCharId(charId));
    }

    public String deleteExistingGameCharById(String jwt, int charId) {
        GameCharEntity charEntity = gameCharRepository.findGameCharEntityByCharId(charId);
        if(charEntity==null){
            throw new IllegalArgumentException("There is no such ID. CharId = " + charId);
        } else if (jwtUserEntityExtractor(jwt)!=charEntity.getUserEntity()){
            throw new IllegalArgumentException("You cannot make changes in other users characters.");
        }
        gameCharRepository.delete(gameCharRepository.findGameCharEntityByCharId(charId));
        return "Character deletion successful.";
    }
    public String deleteExistingCharAsAdmin(int charId) {
        GameCharEntity charEntity = gameCharRepository.findGameCharEntityByCharId(charId);
        if(charEntity==null){
            throw new IllegalArgumentException("There is no such ID. CharId = " + charId);
        }
        gameCharRepository.delete(gameCharRepository.findGameCharEntityByCharId(charId));
        return "Character deletion successful.";
    }

    public List<GameCharResponse> getSameGuildGameChars(String jwt,int charId) {
        if(gameCharRepository.findGameCharEntityByCharId(charId)==null){
            throw new IllegalArgumentException("No character exists for such CharId.");
        }
        GameCharEntity gameCharEntity = gameCharRepository.findGameCharEntityByCharId(charId);
        if(gameCharEntity.getGuildEntity()==null){
            throw new IllegalArgumentException("This character has no guild.");
        }
        int guildId = gameCharEntity.getGuildEntity().getGuildId();
        List<GameCharEntity> gameCharEntitiesOfSameGuild = gameCharRepository.findGameCharEntitiesByGuildEntityGuildId(guildId);
        if(gameCharEntitiesOfSameGuild==null){
            return null;
        }
        List<GameCharResponse> responseList = new ArrayList<>();
        for(GameCharEntity x: gameCharEntitiesOfSameGuild){
            responseList.add(gameCharEntityToResponseMapper(jwt,x));
        }
        return responseList;
    }

    public GameCharResponse setGuildByCharId(String jwt, GameCharGuildJoinRequest request) {
        GameCharEntity foundChar = gameCharRepository.findGameCharEntityByCharId(request.getCharId());
        if(foundChar==null){
            throw new IllegalArgumentException("No character exists for such CharId.");
        } else if (foundChar.getUserEntity()!=jwtUserEntityExtractor(jwt)){
            throw new IllegalArgumentException("You cannot manage other users characters.");
        }
        foundChar.setGuildEntity(guildRepository.findGuildEntityByGuildName(request.getGuildName()));
        foundChar = gameCharRepository.save(foundChar);
        return gameCharEntityToResponseMapper(jwt,foundChar);
    }

    public GameCharResponse gameCharEntityToResponseMapper(String jwt,GameCharEntity entity){
        GameCharResponse response = GameCharResponse.builder()
                .charId(entity.getCharId())
                .charName(entity.getCharName())
                .charLevel(entity.getCharLevel())
                .charGameName(entity.getGameEntity().getGameName())
                .build();
        if(entity.getUserEntity()==null){
            response.setCharOwner(null);
        } else{
            response.setCharOwner(entity.getUserEntity().getDisplayName());
        }
        if(entity.getGameEntity()==null){
            response.setCharGameName(null);
        } else{
            response.setCharGameName(entity.getGameEntity().getGameName());
        }
        if(entity.getGuildEntity()==null){
            response.setCharGuildName(null);
        } else{
            response.setCharGuildName(entity.getGuildEntity().getGuildName());
        }
        response.setCreatedBy(jwtUserEntityExtractor(jwt).getDisplayName());
        response.setCreatedAt(LocalDateTime.now());
        return response;
    }

    public UserEntity jwtUserEntityExtractor(String jwt){
        Map<String, Object> claims = tokenService.decodeJwt(jwt).getClaims();
        return userRepository.findUserEntityByUsername((String)claims.get("sub"));
    }
}
