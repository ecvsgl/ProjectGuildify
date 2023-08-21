package com.guildify.guildify.model.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Builder
@Data
public class PostCommentResponse extends MainResponse{
    private int commentId;
    private String commentContent;
    private String senderDisplayName;
    private int postId;
}
