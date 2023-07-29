package com.guildify.guildify.model.dto;

import com.guildify.guildify.model.GameEntity;
import com.guildify.guildify.model.UserEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameCharResponse extends MainResponse{
    private int charId;
    private String charName;
    private String charLevel;
    private String charOwner;
    private String charGameName;
}
