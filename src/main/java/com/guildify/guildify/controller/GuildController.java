package com.guildify.guildify.controller;

import com.guildify.guildify.model.GuildEntity;
import com.guildify.guildify.service.GuildService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
