package com.guildify.guildify.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CalendarEventResponse extends MainResponse {
    private int eventId;
    private String eventName;
    private LocalDateTime eventTime;
}
