package com.guildify.guildify.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameRequest {
    private String gameName;
    private String gamePublisher;
}
