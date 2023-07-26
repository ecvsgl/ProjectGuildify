package com.guildify.guildify.service;

import com.guildify.guildify.model.GameCharEntity;
import com.guildify.guildify.repository.GameCharRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameCharService {

    @Autowired
    private GameCharRepository gameCharRepository;

    public List<GameCharEntity> getAllGameChars(){return gameCharRepository.findAll();}
}
