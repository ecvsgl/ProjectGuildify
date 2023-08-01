package com.guildify.guildify.controller;

import com.guildify.guildify.model.CalendarEventEntity;
import com.guildify.guildify.model.GameCharEntity;
import com.guildify.guildify.model.GameEntity;
import com.guildify.guildify.model.dto.GameRequest;
import com.guildify.guildify.model.dto.GameResponse;
import com.guildify.guildify.service.CalendarEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class CalenderEventController {

    @Autowired
    private CalendarEventService calendarEventService;

    @GetMapping("/events")
    public List<CalendarEventEntity> getAllCalenderEvents() {
        log.info("Calenders has been searched");
        return calendarEventService.getAllCalenderEvents();
    }

    @GetMapping("/events/{eventId}")
    public CalendarEventEntity getCalenderEventById(@PathVariable int eventId) {
        return calendarEventService.getCalenderEventById(eventId);
    }


    @PostMapping("/events")
    public ResponseEntity<CalendarEventEntity> addNewEvent(@RequestBody CalendarEventEntity event) {
        return ResponseEntity.ok().body(calendarEventService.addNewEvent(event));
    }

    @PutMapping("/events")
    public CalendarEventEntity updateEvent(@RequestBody CalendarEventEntity event) {
        return calendarEventService.updateEvent(event);
    }

    @DeleteMapping("/events")
    public void deleteExistingEvent(@RequestBody CalendarEventEntity event) {
        calendarEventService.deleteExistingEvent(event);
    }
}
