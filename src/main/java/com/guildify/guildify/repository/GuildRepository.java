package com.guildify.guildify.repository;

import com.guildify.guildify.model.GameCharEntity;
import com.guildify.guildify.model.GuildEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GuildRepository extends JpaRepository<GuildEntity, Integer> {

    //
    GuildEntity findGuildEntityByGuildId(int guildId);
}
