package com.guildify.guildify.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class BaseEntity {
    @Column(nullable = false)
    private LocalDateTime timestamp;
    @Column(nullable = false)
    private String createdBy;
}
