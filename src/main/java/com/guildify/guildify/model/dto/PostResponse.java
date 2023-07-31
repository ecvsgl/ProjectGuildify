package com.guildify.guildify.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class PostResponse extends MainResponse{
    private String postContent;
    private LocalDateTime postTimestamp;
    private String postOwnerName;
}