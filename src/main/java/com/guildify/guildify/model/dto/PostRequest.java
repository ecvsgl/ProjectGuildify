package com.guildify.guildify.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostRequest {
    private String postContent;
    private int userId;
}
