package com.guildify.guildify.model.dto;

import com.guildify.guildify.model.GameEntity;
import com.guildify.guildify.model.UserEntity;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameCharResponse extends MainResponse{
    private int charId;
    private String charName;
    private String charLevel;
    private String charOwner;
    private String charGameName;
    private String charGuildName;
}
