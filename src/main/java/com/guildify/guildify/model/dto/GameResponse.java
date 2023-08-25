package com.guildify.guildify.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GameResponse extends MainResponse {
    private int gameId;
    private String gameName;
    private String gamePublisher;
    private List<GameCharResponse> gameChars;
}
