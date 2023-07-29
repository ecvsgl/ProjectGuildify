package com.guildify.guildify.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameRequest {
    private String gameName;
    private String gamePublisher;
}
