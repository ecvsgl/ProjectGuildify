package com.guildify.guildify.initialize;

import com.guildify.guildify.model.CalendarEventEntity;
import com.guildify.guildify.model.PostEntity;
import com.guildify.guildify.model.UserEntity;
import com.guildify.guildify.repository.CalendarEventRepository;
import com.guildify.guildify.repository.PostRepository;
import com.guildify.guildify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class EventInitializerTest implements CommandLineRunner {

    private final CalendarEventRepository calendarEventRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (calendarEventRepository.count() == 0 && userRepository.count()==0 &&
                postRepository.count()==0) {
            //CalendarEvent Persistance...
            CalendarEventEntity event1 = new CalendarEventEntity(1,"EventDeneme1"
                    ,LocalDateTime.of(2023, 7, 24, 12, 30));
            CalendarEventEntity event2 = new CalendarEventEntity(2,"EventDeneme2"
                    ,LocalDateTime.of(2023, 7, 25, 12, 30));
            CalendarEventEntity event3 = new CalendarEventEntity(3,"EventDeneme3"
                    ,LocalDateTime.of(2023, 7, 26, 12, 30));

            calendarEventRepository.save(event1);
            calendarEventRepository.save(event2);
            calendarEventRepository.save(event3);

            //UserEntity Persistance...
            UserEntity user1 = new UserEntity();
            user1.setUsernameHash("hash1");
            user1.setPasswordHash("password1");
            user1.setDisplayName("John Doe");
            user1.setEmail("john.doe@example.com");
            user1.setAccountRank("Standard");
            user1.setTimestamp(LocalDateTime.now());
            user1.setCreatedBy(user1.getDisplayName());

            UserEntity user2 = new UserEntity();
            user2.setUsernameHash("hash2");
            user2.setPasswordHash("password2");
            user2.setDisplayName("Jane Smith");
            user2.setEmail("jane.smith@example.com");
            user2.setAccountRank("Premium");
            user2.setTimestamp(LocalDateTime.now().minusDays(10));
            user2.setCreatedBy(user2.getDisplayName());

            UserEntity user3 = new UserEntity();
            user3.setUsernameHash("hash3");
            user3.setPasswordHash("password3");
            user3.setDisplayName("Mike Johnson");
            user3.setEmail("mike.johnson@example.com");
            user3.setAccountRank("Admin");
            user3.setTimestamp(LocalDateTime.now().minusMonths(1));
            user3.setCreatedBy(user3.getDisplayName());

            UserEntity user4 = new UserEntity();
            user4.setUsernameHash("hash4");
            user4.setPasswordHash("password4");
            user4.setDisplayName("Anna Lee");
            user4.setEmail("anna.lee@example.com");
            user4.setAccountRank("Standard");
            user4.setTimestamp(LocalDateTime.now().minusDays(5));
            user4.setCreatedBy(user4.getDisplayName());

            UserEntity user5 = new UserEntity();
            user5.setUsernameHash("hash5");
            user5.setPasswordHash("password5");
            user5.setDisplayName("David Brown");
            user5.setEmail("david.brown@example.com");
            user5.setAccountRank("Premium");
            user5.setTimestamp(LocalDateTime.now().minusMonths(2));
            user5.setCreatedBy(user5.getDisplayName());
            userRepository.saveAll(List.of(user1, user2, user3, user4, user5));

            //PostEntity Persistance...
            PostEntity postEntity1 = new PostEntity();
            postEntity1.setPostContent("Hello, world!");
            postEntity1.setUserEntity(user5); // Assuming UserEntity constructor exists
            postEntity1.setPostCommentsEntityList(new ArrayList<>());
            postEntity1.setTimestamp(LocalDateTime.now());
            postEntity1.setCreatedBy(postEntity1.getUserEntity().getDisplayName());

            // Example 2
            PostEntity postEntity2 = new PostEntity();
            postEntity2.setPostContent("Check out this amazing photo!");
            postEntity2.setUserEntity(user4); // Assuming UserEntity constructor exists
            postEntity2.setPostCommentsEntityList(new ArrayList<>());
            postEntity2.setTimestamp(LocalDateTime.now());
            postEntity2.setCreatedBy(postEntity2.getUserEntity().getDisplayName());

            // Example 3
            PostEntity postEntity3 = new PostEntity();
            postEntity3.setPostContent("Feeling excited about the upcoming event!");
            postEntity3.setUserEntity(user1); // Assuming UserEntity constructor exists
            postEntity3.setPostCommentsEntityList(new ArrayList<>());
            postEntity3.setTimestamp(LocalDateTime.now());
            postEntity3.setCreatedBy(postEntity3.getUserEntity().getDisplayName());

            // Save the PostEntity instances to the repository
            postRepository.saveAll(List.of(postEntity1,postEntity2,postEntity3));
        }
    }
}
