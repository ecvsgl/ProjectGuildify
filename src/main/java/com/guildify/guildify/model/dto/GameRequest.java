package com.guildify.guildify.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GameRequest {
    private String gameName;
    private String gamePublisher;
}
