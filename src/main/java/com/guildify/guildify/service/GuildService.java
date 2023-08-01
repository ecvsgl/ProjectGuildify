package com.guildify.guildify.service;

import com.guildify.guildify.model.GameCharEntity;
import com.guildify.guildify.model.GuildEntity;
import com.guildify.guildify.model.dto.GuildRequest;
import com.guildify.guildify.model.dto.GuildResponse;
import com.guildify.guildify.repository.GameCharRepository;
import com.guildify.guildify.repository.GuildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

import static com.guildify.guildify.utility.StaticMethods.isNumeric;

@Service
public class GuildService {

    @Autowired
    private GuildRepository guildRepository;

    @Autowired
    private GameCharRepository gameCharRepository;

    public List<GuildEntity> getAllGuilds(){return guildRepository.findAll();}
    public GuildEntity getGuildById(int guildId){
        if(guildRepository.findGuildEntityByGuildId(guildId) == null){
            throw new IllegalArgumentException("There is no such ID. GuildID = " + guildId);
        }
        return guildRepository.findGuildEntityByGuildId(guildId);
    }

    public GuildResponse addNewGuild(GuildRequest guildRequest) {
        if (guildRequest.getGuildName().length() < 1 || isNumeric(guildRequest.getGuildName())) {
            throw new IllegalArgumentException("The guild name cannot be only numeric or lesser than 1 characters");
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

    public GuildEntity updateGuild(int guildId) {
        return guildRepository.save(guildRepository.findGuildEntityByGuildId(guildId));
    }

    public void deleteAllGuilds(GuildEntity guild) {
        guildRepository.delete(guild);
    }

    public void deleteExistingGuild(int guildId) {
        guildRepository.delete(guildRepository.findGuildEntityByGuildId(guildId));
    }

    public List<GameCharEntity> getSameGuildGameChars(int guildId) {
        return (List<GameCharEntity>) gameCharRepository.findGameCharEntitiesByGuildId(guildId);
    }

}
