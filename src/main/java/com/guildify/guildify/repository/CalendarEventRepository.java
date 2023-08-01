package com.guildify.guildify.repository;

import com.guildify.guildify.model.CalendarEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarEventRepository extends JpaRepository<CalendarEventEntity, Integer> {

    CalendarEventEntity findCalendarEventEntityByEventId(int eventId);
}
