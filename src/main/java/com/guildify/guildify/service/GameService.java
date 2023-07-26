package com.guildify.guildify.service;

import com.guildify.guildify.model.GameEntity;
import com.guildify.guildify.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public List<GameEntity> getAllGames(){return gameRepository.findAll();}



}
