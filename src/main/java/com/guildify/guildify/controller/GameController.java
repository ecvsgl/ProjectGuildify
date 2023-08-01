package com.guildify.guildify.controller;

import com.guildify.guildify.model.GameCharEntity;
import com.guildify.guildify.model.GameEntity;
import com.guildify.guildify.model.dto.GameRequest;
import com.guildify.guildify.model.dto.GameResponse;
import com.guildify.guildify.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/games")
    public List<GameEntity> getAllGames() {
        log.info("Games has been searched");
        return gameService.getAllGames();
    }

    @GetMapping("/games/{gameId}")
    public GameEntity getGamesById(@PathVariable int gameId) {
        return gameService.getGamesById(gameId);
    }

    @GetMapping("/games/characters/{gameId}")
    public List<GameCharEntity> getCharsByGameId(@PathVariable int gameId) {
        return gameService.getCharsByGameId(gameId);
    }

    @PostMapping("/games")
    public ResponseEntity<GameResponse> addNewGame(@RequestBody GameRequest game) {
        return ResponseEntity.ok().body(gameService.addNewGame(game));
    }

    @PutMapping("/games")
    public GameEntity updateExistingGame(@RequestBody GameEntity game) {
        return gameService.updateExistingGame(game);
    }

    @DeleteMapping("/games/{gameId}")
    public void deleteExistingGame(@PathVariable int gameId) {
        gameService.deleteExistingGame(gameId);
    }

}
