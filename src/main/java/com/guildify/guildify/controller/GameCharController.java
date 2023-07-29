package com.guildify.guildify.controller;

import com.guildify.guildify.model.GameCharEntity;
import com.guildify.guildify.model.GameEntity;
import com.guildify.guildify.model.dto.GameCharRequest;
import com.guildify.guildify.model.dto.GameCharResponse;
import com.guildify.guildify.model.dto.GameRequest;
import com.guildify.guildify.model.dto.GameResponse;
import com.guildify.guildify.service.GameCharService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class GameCharController {

    @Autowired
    private GameCharService gameCharService;

    @GetMapping("/gamechars")
    public List<GameCharEntity> getAllGameChars() {
        log.info("Game chars has been searched");
        return gameCharService.getAllGameChars();
    }

    @PostMapping("/gamechars")
    public ResponseEntity<GameCharResponse> addNewGameChar(@RequestBody GameCharRequest gameChar) {
        return ResponseEntity.ok().body(gameCharService.addNewGameChar(gameChar));
    }

    @PutMapping("/gamechars")
    public GameCharEntity updateGameChar(@RequestBody GameCharEntity gameChar) {

        return gameCharService.updateGameChar(gameChar);
    }

    @DeleteMapping("/gameschars")
    public void deleteExistingGameChar(@RequestBody GameCharEntity gameChar) {
        gameCharService.deleteExistingGameChar(gameChar);
    }

}
