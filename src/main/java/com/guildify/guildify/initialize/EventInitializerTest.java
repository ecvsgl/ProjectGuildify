package com.guildify.guildify.initialize;

import com.guildify.guildify.model.CalendarEventEntity;
import com.guildify.guildify.repository.CalendarEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
public class EventInitializerTest implements CommandLineRunner {

    private final CalendarEventRepository calendarEventRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (calendarEventRepository.count() == 0) {
            CalendarEventEntity event1 = new CalendarEventEntity(1,"EventDeneme1"
                    ,LocalDateTime.of(2023, 7, 24, 12, 30));
            CalendarEventEntity event2 = new CalendarEventEntity(2,"EventDeneme2"
                    ,LocalDateTime.of(2023, 7, 25, 12, 30));
            CalendarEventEntity event3 = new CalendarEventEntity(3,"EventDeneme3"
                    ,LocalDateTime.of(2023, 7, 26, 12, 30));

            calendarEventRepository.save(event1);
            calendarEventRepository.save(event2);
            calendarEventRepository.save(event3);

        }
    }


}
