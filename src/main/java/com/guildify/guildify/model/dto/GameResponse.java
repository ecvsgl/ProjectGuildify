package com.guildify.guildify.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameResponse extends MainResponse {
    private int gameId;
    private String gameName;
    private String gamePublisher;
}
