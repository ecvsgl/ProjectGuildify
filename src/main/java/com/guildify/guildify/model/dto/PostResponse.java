package com.guildify.guildify.model.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse extends MainResponse{
    private int postId;
    private String postContent;
    private String postOwnerDisplayName;
    private List<PostCommentResponse> postCommentsList;
    private LocalDateTime postTimestamp;
}
