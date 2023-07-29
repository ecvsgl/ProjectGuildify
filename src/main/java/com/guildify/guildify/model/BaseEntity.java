package com.guildify.guildify.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class BaseEntity {
    private LocalDateTime timestamp;
    private String createdBy;
}
