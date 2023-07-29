package com.guildify.guildify.controller;

import com.guildify.guildify.model.GameEntity;
import com.guildify.guildify.model.GuildEntity;
import com.guildify.guildify.model.dto.GameRequest;
import com.guildify.guildify.model.dto.GameResponse;
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

    @GetMapping("/guilds")
    public List<GuildEntity> getAllGuilds() {
        log.info("Guilds has been searched");
        return guildService.getAllGuilds();
    }

    @PostMapping("/guilds")
    public ResponseEntity<GuildResponse> addNewGuild(@RequestBody GuildRequest guild) {
        return ResponseEntity.ok().body(guildService.addNewGuild(guild));
    }

    @PutMapping("/guilds")
    public GuildEntity updateGuild(@RequestBody GuildEntity guild) {
        return guildService.updateGuild(guild);
    }

    @DeleteMapping("/guilds")
    public void deleteExistingGuild(@RequestBody GuildEntity guild) {
        guildService.deleteExistingGuild(guild);
    }
}
