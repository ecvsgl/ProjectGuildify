package com.guildify.guildify.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PostResponse extends MainResponse{
    private int postId;
    private String postContent;
    private String postOwnerDisplayName;
    private List<PostCommentResponse> postCommentsList;
}
