package com.guildify.guildify.service;

import com.guildify.guildify.model.GameEntity;
import com.guildify.guildify.model.dto.GameRequest;
import com.guildify.guildify.model.dto.GameResponse;
import com.guildify.guildify.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public List<GameEntity> getAllGames(){return gameRepository.findAll();}


    public GameResponse addNewGame(GameRequest gameRequest) {
        if (gameRequest.getGameName().length() < 1) {
            throw new IllegalArgumentException("The game name length cannot be lesser than 1 characters");
        }

        // DTO to Entity mapper
        GameEntity gameEntity = GameEntity.builder()
                .gameName(gameRequest.getGameName())
                .gamePublisher(gameRequest.getGamePublisher())
                .build();

        gameEntity = gameRepository.save(gameEntity);

        // Entity to DTO mapper
        GameResponse response = GameResponse.builder()
                .gameId(gameEntity.getGameId())
                .gameName(gameEntity.getGameName())
                .gamePublisher(gameEntity.getGamePublisher())
                .build();

        response.setCreatedAt(LocalDateTime.now());
        response.setCreatedBy("Buraya Giriş yapan kullanıcı değişkeni eklenmelidir.");

        return response;
    }

    public GameEntity updateGame(GameEntity game) {
        return gameRepository.save(game);
    }

    public void deleteExistingGame(GameEntity game) {
        gameRepository.delete(game);
    }
}
