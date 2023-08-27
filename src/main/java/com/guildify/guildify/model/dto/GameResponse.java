package com.guildify.guildify.model.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameResponse extends MainResponse {
    private int gameId;
    private String gameName;
    private String gamePublisher;
    private List<GameCharResponse> gameChars;
}
