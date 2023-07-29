package com.guildify.guildify.model.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MainResponse {
    private LocalDateTime  createdAt;
    private String createdBy;
}
