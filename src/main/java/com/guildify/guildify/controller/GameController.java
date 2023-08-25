package com.guildify.guildify.controller;


import com.guildify.guildify.model.dto.GameCharResponse;
import com.guildify.guildify.model.dto.GameRequest;
import com.guildify.guildify.model.dto.GameResponse;
import com.guildify.guildify.service.GameService;
import com.guildify.guildify.utility.StaticMethods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/user/games")
    public List<GameResponse> getAllGames(@RequestHeader("Authorization") String bearerToken) {
        log.info("Games has been searched");
        return gameService.getAllGames(StaticMethods.getJwtFromRequestHeader(bearerToken));
    }

    @GetMapping("/user/games/{gameId}")
    public GameResponse getGameById(@RequestHeader("Authorization") String bearerToken,
                                     @PathVariable int gameId) {
        return gameService.getGameById(StaticMethods.getJwtFromRequestHeader(bearerToken),gameId);
    }

    @GetMapping("/user/games/characters/{gameId}")
    public List<GameCharResponse> getCharsByGameId(@RequestHeader("Authorization") String bearerToken,
                                                   @PathVariable int gameId) {
        return gameService.getCharsByGameId(StaticMethods.getJwtFromRequestHeader(bearerToken),gameId);
    }

    @PostMapping("/admin/games")
    public GameResponse addNewGame(@RequestHeader("Authorization") String bearerToken,
                                   @RequestBody GameRequest game) {
        return gameService.addNewGame(StaticMethods.getJwtFromRequestHeader(bearerToken),game);
    }

    @PutMapping("/admin/games/{gameId}")
    public GameResponse updateExistingGame(@RequestHeader("Authorization") String bearerToken,
                                           @RequestBody GameRequest gameRequest,
                                           @PathVariable int gameId) {
        return gameService.updateExistingGame(StaticMethods.getJwtFromRequestHeader(bearerToken),gameRequest, gameId);
    }

    @DeleteMapping("/admin/games/{gameId}")
    public String deleteExistingGame(@PathVariable int gameId) {
        return gameService.deleteExistingGame(gameId);
    }

}
