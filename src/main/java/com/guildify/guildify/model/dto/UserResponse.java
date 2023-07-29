package com.guildify.guildify.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserResponse extends MainResponse{
    private Integer userId;
    private String displayName;
    private String email;
    private String accountRank;
    private LocalDateTime accountCreationDate;
    private List<GameCharResponse> gameCharResponseList;
}
