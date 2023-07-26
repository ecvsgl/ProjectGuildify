package com.guildify.guildify.controller;

import com.guildify.guildify.model.CalendarEventEntity;
import com.guildify.guildify.service.CalendarEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
