package com.guildify.guildify.model.dto;

import lombok.*;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentRequest {
    private String commentContent;
    private int postId;
}
