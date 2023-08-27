package com.guildify.guildify.model.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    private String postContent;
}
