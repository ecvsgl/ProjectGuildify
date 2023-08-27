package com.guildify.guildify.model.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse extends MainResponse{
    private Integer userId;
    private String displayName;
    private String email;
    private String accountRank;
    private List<GameCharResponse> gameCharResponseList;
}
