package com.guildify.guildify.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse extends MainResponse{
    private Integer userId;
    private String usernameHash;
    private String passwordHash;
    private String displayName;
    private String displayNameHash;
    private String email;
    private String accountRank;
    private LocalDateTime accountCreationDate;
}
