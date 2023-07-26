package com.guildify.guildify.controller;

import com.guildify.guildify.model.GameCharEntity;
import com.guildify.guildify.service.GameCharService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
