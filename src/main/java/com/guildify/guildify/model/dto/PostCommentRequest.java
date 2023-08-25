package com.guildify.guildify.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PostCommentRequest {
    private String commentContent;
    private int postId;
}
