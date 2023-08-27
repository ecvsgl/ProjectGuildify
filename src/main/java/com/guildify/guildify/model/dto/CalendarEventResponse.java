package com.guildify.guildify.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarEventResponse extends MainResponse {
    private int eventId;
    private String eventName;
    private LocalDateTime eventTime;
}
