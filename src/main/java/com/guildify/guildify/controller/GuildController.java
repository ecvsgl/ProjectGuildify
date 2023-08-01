package com.guildify.guildify.controller;

import com.guildify.guildify.model.GameCharEntity;
import com.guildify.guildify.model.GuildEntity;
import com.guildify.guildify.model.dto.GuildRequest;
import com.guildify.guildify.model.dto.GuildResponse;
import com.guildify.guildify.service.GuildService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class GuildController {

    @Autowired
    private GuildService guildService;

    @PostMapping("/guilds")
    public ResponseEntity<GuildResponse> addNewGuild(@RequestBody GuildRequest guild) {
        return ResponseEntity.ok().body(guildService.addNewGuild(guild));
    }

    @GetMapping("/guilds")
    public List<GuildEntity> getAllGuilds() {
        log.info("Guilds has been searched");
        return guildService.getAllGuilds();
    }

    @GetMapping("/guilds/{guildId}")
    public GuildEntity getGuildById(@PathVariable int guildId) {
        return guildService.getGuildById(guildId);
    }

    @GetMapping("/guilds/sameguild/{guildId}")
    public List<GameCharEntity> getSameGuildGameChars(int guildId) {
        return guildService.getSameGuildGameChars(guildId);
    }

    @PutMapping("/guilds/{guildId}")
    public GuildEntity updateGuild(@PathVariable int guildId) {
        return guildService.updateGuild(guildId);
    }

    //Deleting all guilds
    @DeleteMapping("/guilds")
    public void deleteAllGuilds(@RequestBody GuildEntity guild) {
        guildService.deleteAllGuilds(guild);
    }

    @DeleteMapping("/guilds/{guildId}")
    public String deleteExistingGuild(@PathVariable int guildId){
        guildService.deleteExistingGuild(guildId);
        return "Guild Deleted Successfully. Guild ID = " + guildId;
    }

}
