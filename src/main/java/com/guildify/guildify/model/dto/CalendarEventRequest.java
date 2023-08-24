package com.guildify.guildify.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CalendarEventRequest {
    private String eventName;
    private LocalDateTime eventTime;
}
