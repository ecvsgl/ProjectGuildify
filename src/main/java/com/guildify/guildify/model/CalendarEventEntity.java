package com.guildify.guildify.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventID", nullable = false)
    private int eventId;
    private String eventName;
    private LocalDateTime eventTime;

    @Override
    public String toString() {
        return "CalendarEventEntity{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", eventTime=" + eventTime +
                '}';
    }
}