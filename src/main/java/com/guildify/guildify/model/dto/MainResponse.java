package com.guildify.guildify.model.dto;


import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MainResponse {
    private LocalDateTime createdAt;
    private String createdBy;
}
