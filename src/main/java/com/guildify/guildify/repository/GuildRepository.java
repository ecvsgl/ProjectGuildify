package com.guildify.guildify.repository;

import com.guildify.guildify.model.GuildEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuildRepository extends JpaRepository<GuildEntity, Integer> {

    GuildEntity findGuildEntityByGuildName(String s);
    GuildEntity findGuildEntityByGuildId(int guildId);
}
