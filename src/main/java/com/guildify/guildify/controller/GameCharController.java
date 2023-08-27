package com.guildify.guildify.controller;

import com.guildify.guildify.model.dto.*;
import com.guildify.guildify.service.GameCharService;
import com.guildify.guildify.utility.StaticMethods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class GameCharController {
//Guilde ekleme işini yap
    @Autowired
    private GameCharService gameCharService;

    @PostMapping("/user/gamechars")
    public GameCharResponse addNewGameChar(@RequestHeader("Authorization") String bearerToken,
                                           @RequestBody GameCharRequest gameChar) {
        return gameCharService.addNewGameChar(StaticMethods.getJwtFromRequestHeader(bearerToken), gameChar);
    }

    @GetMapping("/user/gamechars")
    public List<GameCharResponse> getMyAllGameChars(@RequestHeader("Authorization") String bearerToken) {
        return gameCharService.getMyAllGameChars(StaticMethods.getJwtFromRequestHeader(bearerToken));
    }

    @GetMapping("/admin/gamechars")
    public List<GameCharResponse> getAllGameChars(@RequestHeader("Authorization") String bearerToken) {
        log.info("Game chars has been searched");
        return gameCharService.getAllGameChars(StaticMethods.getJwtFromRequestHeader(bearerToken));
    }

    @GetMapping("/user/gamechars/sameguild/{charId}")
    public List<GameCharResponse> getSameGuildGameChars(@RequestHeader("Authorization") String bearerToken,
                                                        @PathVariable int charId) {
        return gameCharService.getSameGuildGameChars(StaticMethods.getJwtFromRequestHeader(bearerToken), charId);
    }

    @GetMapping("/user/gamechars/{charId}")
    public GameCharResponse getGameCharById(@RequestHeader("Authorization") String bearerToken,
                                            @PathVariable int charId) {
        return gameCharService.getGameCharById(StaticMethods.getJwtFromRequestHeader(bearerToken), charId);
    }

    @PutMapping("/user/gamechars/{charId}")
    public GameCharResponse updateGameCharNameById(@RequestHeader("Authorization") String bearerToken,
                                                   @PathVariable int charId,
                                                   @RequestBody String newCharName) {
        return gameCharService.updateGameChar(StaticMethods.getJwtFromRequestHeader(bearerToken),charId,newCharName);
    }

    @PutMapping("/user/gamechars/addguild/")
        public GameCharResponse setGuildByCharId(@RequestHeader("Authorization") String bearerToken,
                                                 @RequestBody GameCharGuildJoinRequest request){
        return gameCharService.setGuildByCharId(StaticMethods.getJwtFromRequestHeader(bearerToken), request);
    }

    @DeleteMapping("/user/gamechars/{charId}")
    public String deleteExistingChar(@RequestHeader("Authorization") String bearerToken,
                                     @PathVariable int charId){
        return gameCharService.deleteExistingGameCharById(StaticMethods.getJwtFromRequestHeader(bearerToken), charId);
    }
    @DeleteMapping("/admin/gamechars/{charId}")
    public String deleteExistingCharAsAdmin(@PathVariable int charId){
        return gameCharService.deleteExistingCharAsAdmin(charId);
    }

}
