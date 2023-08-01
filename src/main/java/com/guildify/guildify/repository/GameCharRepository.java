package com.guildify.guildify.repository;

import com.guildify.guildify.model.GameCharEntity;
import com.guildify.guildify.model.GuildEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameCharRepository extends JpaRepository<GameCharEntity, Integer> {

    GameCharEntity findGameCharEntityByCharId(int guildId);


    @Query("select c from GameCharEntity c where c.guildEntity =:guildId")
    GameCharEntity findGameCharEntitiesByGuildId(int guildId);

    @Query("select c from GameCharEntity c where c.gameEntity =:gameId")
    GameCharEntity findGameCharEntitiesByGameId(int gameId);
}
