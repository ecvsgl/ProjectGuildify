package com.guildify.guildify.service;

import com.guildify.guildify.model.GuildEntity;
import com.guildify.guildify.repository.GuildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuildService {

    @Autowired
    private GuildRepository guildRepository;

    public List<GuildEntity> getAllGuilds(){return guildRepository.findAll();}
}
