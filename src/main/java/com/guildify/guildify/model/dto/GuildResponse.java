package com.guildify.guildify.model.dto;

import com.guildify.guildify.model.GameCharEntity;
import com.guildify.guildify.model.GameEntity;
import com.guildify.guildify.model.UserEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GuildResponse extends MainResponse{

    private int guildId;
    private String guildName;
    private UserEntity guildLeaderUserEntity;
    private GameEntity gameEntity;
    private List<GameCharEntity> gameCharEntityList;

}
