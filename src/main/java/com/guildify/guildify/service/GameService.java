package com.guildify.guildify.service;

import com.guildify.guildify.model.GameCharEntity;
import com.guildify.guildify.model.GameEntity;
import com.guildify.guildify.model.UserEntity;
import com.guildify.guildify.model.dto.GameCharResponse;
import com.guildify.guildify.model.dto.GameRequest;
import com.guildify.guildify.model.dto.GameResponse;
import com.guildify.guildify.repository.GameCharRepository;
import com.guildify.guildify.repository.GameRepository;
import com.guildify.guildify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.guildify.guildify.utility.StaticMethods.isNumeric;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    public List<GameResponse> getAllGames(String jwt){
        if(gameRepository.count()==0){
            return null;
        }
        List<GameResponse> gameResponseList = new ArrayList<>();
        for(GameEntity game: gameRepository.findAll()){
            gameResponseList.add(gameEntityToResponseMapper(jwt,game));
        }
        return gameResponseList;
    }
    public GameResponse addNewGame(String jwt, GameRequest gameRequest) {
        if (gameRequest.getGameName().length() < 1 || isNumeric(gameRequest.getGameName())) {
            throw new IllegalArgumentException("The Game name cannot be only numeric or lesser than 1 characters");
        }
        if(gameRepository.findGameEntityByGameName(gameRequest.getGameName())!=null){
            throw new IllegalArgumentException("Another Game with the same name already exists.");
        }
        // DTO to Entity mapper
        GameEntity gameEntity = GameEntity.builder()
                .gameName(gameRequest.getGameName())
                .gamePublisher(gameRequest.getGamePublisher())
                .build();

        gameEntity.setTimestamp(LocalDateTime.now());
        gameEntity.setCreatedBy(jwtUserEntityExtractor(jwt).getDisplayName());
        gameEntity = gameRepository.save(gameEntity);
        return gameEntityToResponseMapper(jwt,gameEntity);
    }

    public GameResponse updateExistingGame(String jwt, GameRequest gameRequest, int gameId) {
        if(gameRepository.findGameEntityByGameId(gameId)==null){
            throw new IllegalArgumentException("No such game exists for the given Id.");
        }
        GameEntity gameEntity = gameRepository.findGameEntityByGameId(gameId);
        gameEntity.setGameName(gameRequest.getGameName());
        gameEntity.setGamePublisher(gameRequest.getGamePublisher());
        gameEntity.setCreatedBy(jwtUserEntityExtractor(jwt).getUsername());
        gameEntity.setTimestamp(LocalDateTime.now());
        gameEntity = gameRepository.save(gameEntity);
        return gameEntityToResponseMapper(jwt,gameEntity);
    }

    public String deleteExistingGame(int gameId) {
        if(gameRepository.findGameEntityByGameId(gameId)==null){
            throw new IllegalArgumentException("No such game exists for the given Id.");
        }
        gameRepository.delete(gameRepository.findGameEntityByGameId(gameId));
        return "Game deleted successfully.";
    }

    public GameResponse getGameById(String jwt, int gameId) {
        if(gameRepository.findGameEntityByGameId(gameId) == null){
            throw new IllegalArgumentException("There is no such ID. GameID = " + gameId);
        }
        return gameEntityToResponseMapper(jwt,gameRepository.findGameEntityByGameId(gameId));
    }

    public List<GameCharResponse> getCharsByGameId(String jwt, int gameId) {
        if(gameRepository.findGameEntityByGameId(gameId) == null){
            throw new IllegalArgumentException("There is no such ID. GameID = " + gameId);
        } else if (gameRepository.findGameEntityByGameId(gameId).getGameCharEntityList() == null){
            return null;
        }
        List<GameCharResponse> gameCharResponseList = new ArrayList<>();
        for(GameCharEntity x:  gameRepository.findGameEntityByGameId(gameId).getGameCharEntityList()){
            GameCharResponse gameCharResponse = GameCharResponse.builder()
                    .charId(x.getCharId())
                    .charName(x.getCharName())
                    .charLevel(x.getCharLevel())
                    .charOwner(x.getUserEntity().getDisplayName())
                    .charGameName(x.getGameEntity().getGameName())
                    .charGuildName(x.getGuildEntity().getGuildName())
                    .build();
            gameCharResponse.setCreatedBy(jwtUserEntityExtractor(jwt).getDisplayName());
            gameCharResponse.setCreatedAt(LocalDateTime.now());
            gameCharResponseList.add(gameCharResponse);
        }
        return gameCharResponseList;
    }

    public GameResponse gameEntityToResponseMapper(String jwt, GameEntity game){
        GameResponse gameResponse = GameResponse.builder()
                .gameId(game.getGameId())
                .gameName(game.getGameName())
                .gamePublisher(game.getGamePublisher())
                .gameChars(null)
                .build();
        gameResponse.setCreatedAt(LocalDateTime.now());
        gameResponse.setCreatedBy(jwtUserEntityExtractor(jwt).getDisplayName());
        if(game.getGameCharEntityList()!=null){
            List<GameCharResponse> gameCharResponseList = new ArrayList<>();
            for(GameCharEntity x:  game.getGameCharEntityList()){
                GameCharResponse gameCharResponse = GameCharResponse.builder()
                        .charId(x.getCharId())
                        .charName(x.getCharName())
                        .charLevel(x.getCharLevel())
                        .charOwner(null)
                        .charGameName(null)
                        .charGuildName(null)
                        .build();
                if(x.getUserEntity()!=null){
                    gameCharResponse.setCharOwner(x.getUserEntity().getDisplayName());
                }
                if(x.getGameEntity()!=null){
                    gameCharResponse.setCharGameName(x.getGameEntity().getGameName());
                }
                if(x.getGuildEntity()!=null){
                    gameCharResponse.setCharGuildName(x.getGuildEntity().getGuildName());
                }
                gameCharResponse.setCreatedBy(jwtUserEntityExtractor(jwt).getDisplayName());
                gameCharResponse.setCreatedAt(LocalDateTime.now());
                gameCharResponseList.add(gameCharResponse);
            }
            gameResponse.setGameChars(gameCharResponseList);
        }
        return gameResponse;
    }

    public UserEntity jwtUserEntityExtractor(String jwt){
        Map<String, Object> claims = tokenService.decodeJwt(jwt).getClaims();
        return userRepository.findUserEntityByUsername((String)claims.get("sub"));
    }
}
