package com.guildify.guildify.service;

import com.guildify.guildify.model.CalendarEventEntity;
import com.guildify.guildify.model.UserEntity;
import com.guildify.guildify.model.dto.CalendarEventRequest;
import com.guildify.guildify.model.dto.CalendarEventResponse;
import com.guildify.guildify.repository.CalendarEventRepository;
import com.guildify.guildify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CalendarEventService {

    @Autowired
    private CalendarEventRepository calendarEventRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    public List<CalendarEventResponse> getAllCalenderEvents(String jwt) {
        List<CalendarEventEntity> sortedEntities = calendarEventRepository.findAll().stream()
                .sorted(Comparator.comparing(CalendarEventEntity::getEventTime))
                .collect(Collectors.toList());

        List<CalendarEventResponse> responseList = new ArrayList<>();
        for(CalendarEventEntity x : sortedEntities){
            responseList.add(calendarEventEntityToResponseMapper(jwt, x));
        }
        return responseList;
    }
    public CalendarEventResponse addNewEvent(String jwt, CalendarEventRequest calendarEventRequest) {
        CalendarEventEntity calendarEventEntity = CalendarEventEntity.builder()
                .eventName(calendarEventRequest.getEventName())
                .eventTime(calendarEventRequest.getEventTime())
                .build();
        calendarEventEntity.setCreatedBy(jwtUserEntityExtractor(jwt).getUsername());
        calendarEventEntity.setTimestamp(LocalDateTime.now());
        calendarEventEntity = calendarEventRepository.save(calendarEventEntity);
        return calendarEventEntityToResponseMapper(jwt,calendarEventEntity);
    }

    public CalendarEventResponse updateEvent(String jwt, CalendarEventRequest calendarEventRequest, int eventId) {
        if(calendarEventRepository.findCalendarEventEntityByEventId(eventId)==null){
            throw new IllegalArgumentException("No event exists for such ID.");
        }
        CalendarEventEntity event = calendarEventRepository.findCalendarEventEntityByEventId(eventId);
        event.setEventName(calendarEventRequest.getEventName());
        event.setEventTime(calendarEventRequest.getEventTime());
        event.setCreatedBy(jwtUserEntityExtractor(jwt).getUsername());
        event.setTimestamp(LocalDateTime.now());
        event = calendarEventRepository.save(event);
        return calendarEventEntityToResponseMapper(jwt,event);
    }

    public String deleteExistingEvent(int eventId) {
        if(calendarEventRepository.findCalendarEventEntityByEventId(eventId)==null){
            throw new IllegalArgumentException("No event exists for such ID.");
        }
        calendarEventRepository.delete(calendarEventRepository.findCalendarEventEntityByEventId(eventId));
        return "Event deleted successfully.";
    }

    public CalendarEventResponse getCalenderEventById(String jwt, int eventId) {
        if(calendarEventRepository.findCalendarEventEntityByEventId(eventId)==null){
            throw new IllegalArgumentException("No event exists for such ID.");
        }
        return calendarEventEntityToResponseMapper(jwt,calendarEventRepository.findCalendarEventEntityByEventId(eventId));
    }
    public CalendarEventResponse calendarEventEntityToResponseMapper(String jwt, CalendarEventEntity event){
        CalendarEventResponse response = CalendarEventResponse.builder()
                .eventId(event.getEventId())
                .eventName(event.getEventName())
                .eventTime(event.getEventTime())
                .build();
        response.setCreatedBy(jwtUserEntityExtractor(jwt).getUsername());
        response.setCreatedAt(LocalDateTime.now());
        return response;
    }

    public UserEntity jwtUserEntityExtractor(String jwt){
        Map<String, Object> claims = tokenService.decodeJwt(jwt).getClaims();
        return userRepository.findUserEntityByUsername((String)claims.get("sub"));
    }
}
