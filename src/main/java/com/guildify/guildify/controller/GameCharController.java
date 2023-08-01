package com.guildify.guildify.controller;

import com.guildify.guildify.model.GameCharEntity;
import com.guildify.guildify.model.GameEntity;
import com.guildify.guildify.model.GuildEntity;
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
//Guilde ekleme işini yap
    @Autowired
    private GameCharService gameCharService;

    @PostMapping("/gamechars")
    public ResponseEntity<GameCharResponse> addNewGameChar(@RequestBody GameCharRequest gameChar) {
        return ResponseEntity.ok().body(gameCharService.addNewGameChar(gameChar));
    }


    @GetMapping("/gamechars")
    public List<GameCharEntity> getAllGameChars() {
        log.info("Game chars has been searched");
        return gameCharService.getAllGameChars();
    }

    @GetMapping("/gamechars/sameguild/{charId}")
    public List<GameCharEntity> getSameGuildGameChars(int charId) {
        return gameCharService.getSameGuildGameChars(charId);
    }

    @GetMapping("/gamechars/{charId}")
    public GameCharEntity getGameCharById(@PathVariable int charId) {
        return gameCharService.getGameCharById(charId);
    }

    @PutMapping("/gamechars/{charId}")
    public GameCharEntity updateGameCharNameById(@PathVariable int charId,
                                             @RequestBody String newCharName) {
        return gameCharService.updateGameChar(charId,newCharName);
    }

    @PutMapping("/gamechars/addguild/{charId}")
        public GameCharEntity setGuildByCharId(@PathVariable int charId, int guildId){
        return gameCharService.setGuildByCharId(charId,guildId);
    }

    @DeleteMapping("/gameschars")
    public void deleteExistingGameChar(@RequestBody GameCharEntity gameChar) {
        gameCharService.deleteExistingGameChar(gameChar);
    }

    @DeleteMapping("/gamechars/{charId}")
    public String deleteExistingChar(@PathVariable int charId){
        gameCharService.deleteExistingGameCharById(charId);
        return "Char Deleted Successfully. Char ID = " + charId;
    }

}
