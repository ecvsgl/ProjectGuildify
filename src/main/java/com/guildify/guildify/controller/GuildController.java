package com.guildify.guildify.controller;


import com.guildify.guildify.model.dto.GameCharResponse;
import com.guildify.guildify.model.dto.GuildRequest;
import com.guildify.guildify.model.dto.GuildResponse;
import com.guildify.guildify.service.GuildService;
import com.guildify.guildify.utility.StaticMethods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class GuildController {

    @Autowired
    private GuildService guildService;

    @PostMapping("/user/newguild")
    public GuildResponse addNewGuild(@RequestHeader("Authorization") String bearerToken,
                                                     @RequestBody GuildRequest guild) {
        return guildService.addNewGuild(StaticMethods.getJwtFromRequestHeader(bearerToken), guild);
    }

    @GetMapping("/user/guilds")
    public List<GuildResponse> getAllGuilds(@RequestHeader("Authorization") String bearerToken) {
        log.info("Guilds has been searched");
        return guildService.getAllGuilds(StaticMethods.getJwtFromRequestHeader(bearerToken));
    }

    @GetMapping("/user/guilds/{guildId}")
    public GuildResponse getGuildById(@RequestHeader("Authorization") String bearerToken,
                                    @PathVariable int guildId) {
        return guildService.getGuildById(StaticMethods.getJwtFromRequestHeader(bearerToken),guildId);
    }

    @GetMapping("/user/guilds/sameguild/{guildId}")
    public List<GameCharResponse> getSameGuildGameChars(@RequestHeader("Authorization") String bearerToken,
                                                        @PathVariable int guildId) {
        return guildService.getSameGuildGameChars(StaticMethods.getJwtFromRequestHeader(bearerToken),guildId);
    }

    @PutMapping("/admin/guild/{guildId}")
    public GuildResponse updateGuild(@RequestHeader("Authorization") String bearerToken,
                                     @RequestBody GuildRequest guildRequest,
                                     @PathVariable int guildId) {
        return guildService.updateGuild(StaticMethods.getJwtFromRequestHeader(bearerToken),guildRequest, guildId);
    }

    //Deleting all guilds
    @DeleteMapping("/admin/removeguild/all")
    public String deleteAllGuilds() {
        return guildService.deleteAllGuilds();
    }

    @DeleteMapping("/user/removeguild/{guildId}")
    public String deleteExistingGuild(@RequestHeader("Authorization") String bearerToken,
                                      @PathVariable int guildId){
        return guildService.deleteExistingGuild(StaticMethods.getJwtFromRequestHeader(bearerToken), guildId);
    }

    @DeleteMapping("/admin/removeguild/{guildId}")
    public String deleteExistingGuildAsAdmin(@PathVariable int guildId){
        return guildService.deleteExistingGuildAsAdmin(guildId);
    }

}
