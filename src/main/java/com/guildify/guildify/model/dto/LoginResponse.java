package com.guildify.guildify.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginResponse {
    private UserResponse userResponse;
    private String jwt;

    @Override
    public String toString() {
        return "LoginResponse{" +
                "userResponse=" + userResponse +
                ", jwt='" + jwt + '\'' +
                '}';
    }
}
