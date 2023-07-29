package com.guildify.guildify.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserRequest {
    private String username;
    private String password;
    private String displayName;
    private String email;
}
