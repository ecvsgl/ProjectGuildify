package com.guildify.guildify.service;

import com.guildify.guildify.model.GameEntity;
import com.guildify.guildify.model.GuildEntity;
import com.guildify.guildify.model.dto.GameRequest;
import com.guildify.guildify.model.dto.GameResponse;
import com.guildify.guildify.model.dto.GuildRequest;
import com.guildify.guildify.model.dto.GuildResponse;
import com.guildify.guildify.repository.GuildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GuildService {

    @Autowired
    private GuildRepository guildRepository;

    public List<GuildEntity> getAllGuilds(){return guildRepository.findAll();}

    public GuildResponse addNewGuild(GuildRequest guildRequest) {
        if (guildRequest.getGuildName().length() < 1) {
            throw new IllegalArgumentException("The guild name length cannot be lesser than 1 characters");
        }

        // DTO to Entity mapper
        GuildEntity guildEntity = GuildEntity.builder()
                .guildName(guildRequest.getGuildName())
                .guildLeaderUserEntity(guildRequest.getGuildLeaderUserEntity())
                .gameEntity(guildRequest.getGameEntity())
                .gameCharEntityList(guildRequest.getGameCharEntityList())
                .build();

        guildEntity.setTimestamp(LocalDateTime.now());
        guildEntity.setCreatedBy("Buraya Giriş yapan kullanıcı değişkeni eklenmelidir.");

        guildEntity = guildRepository.save(guildEntity);

        // Entity to DTO mapper
        GuildResponse response = GuildResponse.builder()
                .guildId(guildEntity.getGuildId())
                .guildName(guildEntity.getGuildName())
                .gameEntity(guildEntity.getGameEntity())
                .gameCharEntityList(guildEntity.getGameCharEntityList())
                .guildLeaderUserEntity(guildEntity.getGuildLeaderUserEntity())
                .build();

        response.setCreatedAt(LocalDateTime.now());
        response.setCreatedBy("X kullanıcısı tarafından oluşturuldu");

        return response;
    }

    public GuildEntity updateGuild(GuildEntity guild) {
        return guildRepository.save(guild);
    }

    public void deleteExistingGuild(GuildEntity guild) {
        guildRepository.delete(guild);
    }
}
