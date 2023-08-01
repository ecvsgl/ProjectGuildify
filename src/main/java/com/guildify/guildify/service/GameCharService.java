package com.guildify.guildify.service;

import com.guildify.guildify.model.GameCharEntity;
import com.guildify.guildify.model.dto.GameCharRequest;
import com.guildify.guildify.model.dto.GameCharResponse;
import com.guildify.guildify.repository.GameCharRepository;
import com.guildify.guildify.repository.GuildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.guildify.guildify.utility.StaticMethods.isNumeric;

@Service
public class GameCharService {

    @Autowired
    private GameCharRepository gameCharRepository;

    @Autowired
    private GuildRepository guildRepository;

    public List<GameCharEntity> getAllGameChars(){return gameCharRepository.findAll();}

    public GameCharResponse addNewGameChar(GameCharRequest gameCharRequest) {
        if (gameCharRequest.getCharName().length() < 1 || isNumeric(gameCharRequest.getCharName())) {
            throw new IllegalArgumentException("The Character name cannot be only numeric or lesser than 1 characters");
        }

        // DTO to Entity mapper
        GameCharEntity gameCharEntity = GameCharEntity.builder()
                .charName(gameCharRequest.getCharName())
                .charLevel(gameCharRequest.getCharLevel())
                .userEntity(gameCharRequest.getUserEntity())
                .gameEntity(gameCharRequest.getGameEntity())
                .build();

        gameCharEntity.setTimestamp(LocalDateTime.now());
        gameCharEntity.setCreatedBy("Buraya Giriş yapan kullanıcı değişkeni eklenmelidir.");

        gameCharEntity = gameCharRepository.save(gameCharEntity);

        // Entity to DTO mapper
        GameCharResponse response = GameCharResponse.builder()
                .charId(gameCharEntity.getCharId())
                .charName(gameCharEntity.getCharName())
                .charLevel(gameCharEntity.getCharLevel())
                .build();

        response.setCharOwner(gameCharEntity.getUserEntity().getUsernameHash());
        response.setCharGameName(gameCharEntity.getGameEntity().getGameName());
        response.setCreatedAt(LocalDateTime.now());
        response.setCreatedBy("X kullanıcı değişkeni eklenmelidir.");

        return response;
    }

    public GameCharEntity updateGameChar(int charId, String newCharName) {
        GameCharEntity foundCharacter = gameCharRepository.findGameCharEntityByCharId(charId);
        foundCharacter.setCharName(newCharName);

        return gameCharRepository.save(foundCharacter);
    }

    public void deleteExistingGameChar(GameCharEntity gameChar) {
    }

    public GameCharEntity getGameCharById(int charId) {
        if(gameCharRepository.findGameCharEntityByCharId(charId) == null){
            throw new IllegalArgumentException("There is no such ID. CharId = " + charId);
        }
        return gameCharRepository.findGameCharEntityByCharId(charId);
    }

    public void deleteExistingGameCharById(int charId) {
        gameCharRepository.delete(gameCharRepository.findGameCharEntityByCharId(charId));
    }

    public List<GameCharEntity> getSameGuildGameChars(int charId) {
        int guildId = gameCharRepository.findGameCharEntityByCharId(charId).getGuildEntity().getGuildId();
        return (List<GameCharEntity>) gameCharRepository.findGameCharEntitiesByGuildId(guildId);
    }

    public GameCharEntity setGuildByCharId(int charId, int guildId) {
        GameCharEntity foundChar = gameCharRepository.findGameCharEntityByCharId(charId);
        foundChar.setGuildEntity(guildRepository.findGuildEntityByGuildId(guildId));
        return foundChar;
    }
}
