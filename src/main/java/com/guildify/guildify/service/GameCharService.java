package com.guildify.guildify.service;

import com.guildify.guildify.model.GameCharEntity;
import com.guildify.guildify.model.GameEntity;
import com.guildify.guildify.model.dto.GameCharRequest;
import com.guildify.guildify.model.dto.GameCharResponse;
import com.guildify.guildify.model.dto.GameRequest;
import com.guildify.guildify.model.dto.GameResponse;
import com.guildify.guildify.repository.GameCharRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GameCharService {

    @Autowired
    private GameCharRepository gameCharRepository;

    public List<GameCharEntity> getAllGameChars(){return gameCharRepository.findAll();}

    public GameCharResponse addNewGameChar(GameCharRequest gameCharRequest) {
        if (gameCharRequest.getCharName().length() < 1) {
            throw new IllegalArgumentException("The game char name length cannot be lesser than 1 characters");
        }

        // DTO to Entity mapper
        GameCharEntity gameCharEntity = GameCharEntity.builder()
                .charName(gameCharRequest.getCharName())
                .charLevel(gameCharRequest.getCharLevel())
                .userEntity(gameCharRequest.getUserEntity())
                .gameEntity(gameCharRequest.getGameEntity())
                .build();

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
        response.setCreatedBy("Buraya Giriş yapan kullanıcı değişkeni eklenmelidir.");

        return response;
    }

    public GameCharEntity updateGameChar(GameCharEntity gameChar) {
        return gameCharRepository.save(gameChar);
    }

    public void deleteExistingGameChar(GameCharEntity gameChar) {
    }
}
