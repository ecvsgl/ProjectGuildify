package com.guildify.guildify.repository;

import com.guildify.guildify.model.GameCharEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameCharRepository extends JpaRepository<GameCharEntity, Integer> {

    GameCharEntity findGameCharEntityByCharId(int charId);
    List<GameCharEntity> findGameCharEntitiesByGuildEntityGuildId(int guildId);
}
