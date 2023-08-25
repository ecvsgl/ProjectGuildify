package com.guildify.guildify.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameCharGuildJoinRequest {
    private int charId;
    private String guildName;
}
