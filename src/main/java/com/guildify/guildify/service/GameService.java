package com.guildify.guildify.service;

import com.guildify.guildify.model.GameCharEntity;
import com.guildify.guildify.model.GameEntity;
import com.guildify.guildify.model.dto.GameRequest;
import com.guildify.guildify.model.dto.GameResponse;
import com.guildify.guildify.repository.GameCharRepository;
import com.guildify.guildify.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.guildify.guildify.utility.StaticMethods.isNumeric;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameCharRepository gameCharRepository;

    public List<GameEntity> getAllGames(){return gameRepository.findAll();}


    public GameResponse addNewGame(GameRequest gameRequest) {
        if (gameRequest.getGameName().length() < 1 || isNumeric(gameRequest.getGameName())) {
            throw new IllegalArgumentException("The Game name cannot be only numeric or lesser than 1 characters");
        }

        // DTO to Entity mapper
        GameEntity gameEntity = GameEntity.builder()
                .gameName(gameRequest.getGameName())
                .gamePublisher(gameRequest.getGamePublisher())
                .build();

        gameEntity.setTimestamp(LocalDateTime.now());
        gameEntity.setCreatedBy("Buraya Giriş yapan kullanıcı değişkeni eklenmelidir.");

        gameEntity = gameRepository.save(gameEntity);

        // Entity to DTO mapper
        GameResponse response = GameResponse.builder()
                .gameId(gameEntity.getGameId())
                .gameName(gameEntity.getGameName())
                .gamePublisher(gameEntity.getGamePublisher())
                .build();

        response.setCreatedAt(LocalDateTime.now());
        response.setCreatedBy("X kullanıcısı tarafından oluşturuldu");

        return response;
    }

    public GameEntity updateExistingGame(GameEntity game) {
        return gameRepository.save(game);
    }

    public void deleteExistingGame(int gameId) {
        gameRepository.delete(gameRepository.findGameEntityByGameId(gameId));
    }

    public GameEntity getGamesById(int gameId) {
        if(gameRepository.findGameEntityByGameId(gameId) == null){
            throw new IllegalArgumentException("There is no such ID. GameID = " + gameId);
        }
        return gameRepository.findGameEntityByGameId(gameId);
    }

    public List<GameCharEntity> getCharsByGameId(int gameId) {
        return (List<GameCharEntity>) gameCharRepository.findGameCharEntitiesByGameId(gameId);
    }
}
