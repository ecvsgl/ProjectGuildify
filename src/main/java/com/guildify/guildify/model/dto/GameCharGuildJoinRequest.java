package com.guildify.guildify.model.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameCharGuildJoinRequest {
    private int charId;
    private String guildName;
}
