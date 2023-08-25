package com.guildify.guildify.controller;


import com.guildify.guildify.model.dto.CalendarEventRequest;
import com.guildify.guildify.model.dto.CalendarEventResponse;
import com.guildify.guildify.service.CalendarEventService;
import com.guildify.guildify.utility.StaticMethods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class CalenderEventController {

    @Autowired
    private CalendarEventService calendarEventService;

    @GetMapping("/user/events")
    public List<CalendarEventResponse> getAllCalenderEvents(@RequestHeader("Authorization") String bearerToken) {
        log.info("Calenders has been searched");
        return calendarEventService.getAllCalenderEvents(StaticMethods.getJwtFromRequestHeader(bearerToken));
    }

    @GetMapping("/admin/events/{eventId}")
    public CalendarEventResponse getCalenderEventById(@RequestHeader("Authorization") String bearerToken,
                                                      @PathVariable int eventId) {
        return calendarEventService.getCalenderEventById(StaticMethods.getJwtFromRequestHeader(bearerToken),eventId);
    }

    @PostMapping("/admin/newevent")
    public CalendarEventResponse addNewEvent(@RequestHeader("Authorization") String bearerToken,
                                             @RequestBody CalendarEventRequest event) {
        return calendarEventService.addNewEvent(StaticMethods.getJwtFromRequestHeader(bearerToken),event);
    }

    @PutMapping("/admin/updateevent/{eventId}")
    public CalendarEventResponse updateEvent(@RequestHeader("Authorization") String bearerToken,
                                             @RequestBody CalendarEventRequest event,
                                             @PathVariable int eventId) {
        return calendarEventService.updateEvent(StaticMethods.getJwtFromRequestHeader(bearerToken),event,eventId);
    }

    @DeleteMapping("/admin/removeevent/{eventId}") //Better get as eventId since name is not unique.
    public String deleteExistingEvent(@PathVariable int eventId) {
        return calendarEventService.deleteExistingEvent(eventId);
    }
}
