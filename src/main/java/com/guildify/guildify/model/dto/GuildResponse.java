package com.guildify.guildify.model.dto;

import com.guildify.guildify.model.GameCharEntity;
import com.guildify.guildify.model.GameEntity;
import com.guildify.guildify.model.UserEntity;
import lombok.*;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuildResponse extends MainResponse{
    private int guildId;
    private String guildName;
    private String guildLeaderUserDisplayName;
    private String gameName;
    private List<GameCharResponse> gameCharResponseList;
}
