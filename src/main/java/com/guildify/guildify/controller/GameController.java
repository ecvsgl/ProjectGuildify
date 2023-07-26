package com.guildify.guildify.controller;

import com.guildify.guildify.model.GameEntity;
import com.guildify.guildify.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
