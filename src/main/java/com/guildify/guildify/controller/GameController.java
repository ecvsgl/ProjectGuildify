package com.guildify.guildify.controller;

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
    @PostMapping("/games")
    public ResponseEntity<GameResponse> addNewGame(@RequestBody GameRequest game) {
        return ResponseEntity.ok().body(gameService.addNewGame(game));
    }

    @PutMapping("/games")
    public GameEntity updateGame(@RequestBody GameEntity game) {
        return gameService.updateGame(game);
    }

    @DeleteMapping("/games")
    public void deleteExistingGame(@RequestBody GameEntity game) {
        gameService.deleteExistingGame(game);
    }

}
