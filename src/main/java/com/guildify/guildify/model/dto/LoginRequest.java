package com.guildify.guildify.model.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class LoginRequest {
    private String username;
    private String password;
}
