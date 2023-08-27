package com.guildify.guildify.model.dto;

import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentResponse extends MainResponse{
    private int commentId;
    private String commentContent;
    private String senderDisplayName;
    private int postId;
    private LocalDateTime commentTimestamp;
}
