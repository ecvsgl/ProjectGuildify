package com.guildify.guildify.service;

import com.guildify.guildify.model.CalendarEventEntity;
import com.guildify.guildify.repository.CalendarEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarEventService {

    @Autowired
    private CalendarEventRepository calendarEventRepository;

    public List<CalendarEventEntity> getAllCalenderEvents() {
        return calendarEventRepository.findAll();
    }

    public CalendarEventEntity addNewEvent(CalendarEventEntity event) {
        return calendarEventRepository.save(event);
    }

    public CalendarEventEntity updateEvent(CalendarEventEntity event) {
        return calendarEventRepository.save(event);
    }

    public void deleteExistingEvent(CalendarEventEntity event) {
        calendarEventRepository.delete(event);
    }

    public CalendarEventEntity getCalenderEventById(int eventId) {

        return calendarEventRepository.findCalendarEventEntityByEventId(eventId);
    }
}
