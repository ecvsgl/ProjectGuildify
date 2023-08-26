package com.guildify.guildify.initialize;

import com.guildify.guildify.model.*;
import com.guildify.guildify.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
@RequiredArgsConstructor
public class EventInitializerTest implements CommandLineRunner {

    private final CalendarEventRepository calendarEventRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostCommentsRepository postCommentsRepository;
    private final GuildRepository guildRepository;
    private final GameCharRepository gameCharRepository;
    private final GameRepository gameRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (calendarEventRepository.count() == 0 &&
                userRepository.count()==0 &&
                postRepository.count()==0 &&
                postCommentsRepository.count()==0 &&
                guildRepository.count()==0 &&
                gameRepository.count()==0 &&
                gameCharRepository.count()==0 &&
                roleRepository.count()==0) {
            //CalendarEvent Persistance...
            CalendarEventEntity event1 = new CalendarEventEntity(1,"EventDeneme1"
                    ,LocalDateTime.of(2023, 7, 24, 12, 30));
            CalendarEventEntity event2 = new CalendarEventEntity(2,"EventDeneme2"
                    ,LocalDateTime.of(2023, 7, 25, 12, 30));
            CalendarEventEntity event3 = new CalendarEventEntity(3,"EventDeneme3"
                    ,LocalDateTime.of(2023, 7, 26, 12, 30));
            event1.setCreatedBy("System");
            event1.setTimestamp(LocalDateTime.now());
            event2.setCreatedBy("System");
            event2.setTimestamp(LocalDateTime.now());
            event3.setCreatedBy("System");
            event3.setTimestamp(LocalDateTime.now());
            calendarEventRepository.save(event1);
            calendarEventRepository.save(event2);
            calendarEventRepository.save(event3);

            //RolePersistance
            Role standardRole = new Role();
            standardRole.setAuthority("STANDARD_USER");
            Set<Role> standardAuthoritySet = new HashSet<>();
            standardAuthoritySet.add(standardRole);

            Role adminRole = new Role();
            adminRole.setAuthority("ADMIN");
            Set<Role> adminAuthoritySet = new HashSet<>();
            adminAuthoritySet.add(adminRole);

            roleRepository.saveAll(List.of(standardRole,adminRole));

            //UserEntity Persistance...
            UserEntity admin = new UserEntity();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setDisplayName("Admin");
            admin.setEmail("admin@guildify.com");
            admin.setAccountRank("Admin");
            admin.setTimestamp(LocalDateTime.now());
            admin.setCreatedBy(admin.getDisplayName());
            admin.setAuthorities(adminAuthoritySet);

            UserEntity user1 = new UserEntity();
            user1.setUsername("user1");
            user1.setPassword(passwordEncoder.encode("password1"));
            user1.setDisplayName("JohnDoe");
            user1.setEmail("john.doe@example.com");
            user1.setAccountRank("Standard");
            user1.setTimestamp(LocalDateTime.now());
            user1.setCreatedBy(user1.getDisplayName());
            user1.setAuthorities(standardAuthoritySet);

            UserEntity user2 = new UserEntity();
            user2.setUsername("user2");
            user2.setPassword(passwordEncoder.encode("password2"));
            user2.setDisplayName("JaneSmith");
            user2.setEmail("jane.smith@example.com");
            user2.setAccountRank("Premium");
            user2.setTimestamp(LocalDateTime.now().minusDays(10));
            user2.setCreatedBy(user2.getDisplayName());
            user2.setAuthorities(standardAuthoritySet);

            UserEntity user3 = new UserEntity();
            user3.setUsername("user3");
            user3.setPassword(passwordEncoder.encode("password3"));
            user3.setDisplayName("MikeJohnson");
            user3.setEmail("mike.johnson@example.com");
            user3.setAccountRank("Bebe");
            user3.setTimestamp(LocalDateTime.now().minusMonths(1));
            user3.setCreatedBy(user3.getDisplayName());
            user3.setAuthorities(standardAuthoritySet);

            UserEntity user4 = new UserEntity();
            user4.setUsername("user4");
            user4.setPassword(passwordEncoder.encode("password4"));
            user4.setDisplayName("AnnaLee");
            user4.setEmail("anna.lee@example.com");
            user4.setAccountRank("Standard");
            user4.setTimestamp(LocalDateTime.now().minusDays(5));
            user4.setCreatedBy(user4.getDisplayName());
            user4.setAuthorities(standardAuthoritySet);

            UserEntity user5 = new UserEntity();
            user5.setUsername("user5");
            user5.setPassword(passwordEncoder.encode("password5"));
            user5.setDisplayName("DavidBrown");
            user5.setEmail("david.brown@example.com");
            user5.setAccountRank("Premium");
            user5.setTimestamp(LocalDateTime.now().minusMonths(2));
            user5.setCreatedBy(user5.getDisplayName());
            user5.setAuthorities(standardAuthoritySet);

            userRepository.saveAll(List.of(admin, user1, user2, user3, user4, user5));

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

            // Example 1
            PostCommentsEntity postCommentsEntity1 = PostCommentsEntity.builder()
                    .commentContent("Great post!")
                    .userEntity(userRepository.findUserEntityByUserId(3))
                    .postEntity(postRepository.findPostEntityByPostId(1))
                    .build();
            postCommentsEntity1.setTimestamp(LocalDateTime.now());
            postCommentsEntity1.setCreatedBy(postCommentsEntity1.getUserEntity().getDisplayName());

            // Example 2
            PostCommentsEntity postCommentsEntity2 = PostCommentsEntity.builder()
                    .commentContent("Thanks for sharing.")
                    .userEntity(userRepository.findUserEntityByUserId(4))
                    .postEntity(postRepository.findPostEntityByPostId(1))
                    .build();
            postCommentsEntity2.setTimestamp(LocalDateTime.now());
            postCommentsEntity2.setCreatedBy(postCommentsEntity2.getUserEntity().getDisplayName());

            // Example 3
            PostCommentsEntity postCommentsEntity3 = PostCommentsEntity.builder()
                    .commentContent("Looking forward to it!")
                    .userEntity(userRepository.findUserEntityByUserId(2))
                    .postEntity(postRepository.findPostEntityByPostId(2))
                    .build();
            postCommentsEntity3.setTimestamp(LocalDateTime.now());
            postCommentsEntity3.setCreatedBy(postCommentsEntity3.getUserEntity().getDisplayName());

            // Save the PostCommentsEntity instances to the repository
            postCommentsRepository.saveAll(List.of(postCommentsEntity1,postCommentsEntity2,postCommentsEntity3));

            // GameEntity Persistence
            GameEntity game1 = new GameEntity();
            game1.setGameName("Guild Wars");
            game1.setGamePublisher("ArenaNet");
            game1.setCreatedBy("System");
            game1.setTimestamp(LocalDateTime.now());

            GameEntity game2 = new GameEntity();
            game2.setGameName("World of Warcraft");
            game2.setGamePublisher("Blizzard Entertainment");
            game2.setCreatedBy("System");
            game2.setTimestamp(LocalDateTime.now());

            GameEntity game3 = new GameEntity();
            game3.setGameName("Final Fantasy XIV");
            game3.setGamePublisher("Square Enix");
            game3.setCreatedBy("System");
            game3.setTimestamp(LocalDateTime.now());

            GameEntity game4 = new GameEntity();
            game4.setGameName("Elder Scrolls Online");
            game4.setGamePublisher("Bethesda Softworks");
            game4.setCreatedBy("System");
            game4.setTimestamp(LocalDateTime.now());

            gameRepository.saveAll(List.of(game1, game2, game3, game4));

            // GuildEntity Persistence
            GuildEntity guild1 = new GuildEntity();
            guild1.setGuildName("Warrior Guild");
            guild1.setGuildLeaderUserEntity(user1);
            guild1.setGameEntity(game1);
            guild1.setCreatedBy("System");
            guild1.setTimestamp(LocalDateTime.now());

            GuildEntity guild2 = new GuildEntity();
            guild2.setGuildName("Mage Association");
            guild2.setGuildLeaderUserEntity(user2);
            guild2.setGameEntity(game2);
            guild2.setCreatedBy("System");
            guild2.setTimestamp(LocalDateTime.now());

            GuildEntity guild3 = new GuildEntity();
            guild3.setGuildName("Ranger Circle");
            guild3.setGuildLeaderUserEntity(user3);
            guild3.setGameEntity(game3);
            guild3.setCreatedBy("System");
            guild3.setTimestamp(LocalDateTime.now());

            GuildEntity guild4 = new GuildEntity();
            guild4.setGuildName("Healer's Covenant");
            guild4.setGuildLeaderUserEntity(user4);
            guild4.setGameEntity(game4);
            guild4.setCreatedBy("System");
            guild4.setTimestamp(LocalDateTime.now());

            guildRepository.saveAll(List.of(guild1, guild2, guild3, guild4));


            // GameCharEntity Persistence
            GameCharEntity char1 = new GameCharEntity();
            char1.setCharName("Aragorn");
            char1.setCharLevel("50");
            char1.setUserEntity(user1);
            char1.setGameEntity(game1);
            char1.setGuildEntity(guild1);
            char1.setCreatedBy("System");
            char1.setTimestamp(LocalDateTime.now());

            GameCharEntity char2 = new GameCharEntity();
            char2.setCharName("Gandalf");
            char2.setCharLevel("60");
            char2.setUserEntity(user2);
            char2.setGameEntity(game2);
            char2.setGuildEntity(guild2);
            char2.setCreatedBy("System");
            char2.setTimestamp(LocalDateTime.now());

            GameCharEntity char3 = new GameCharEntity();
            char3.setCharName("Legolas");
            char3.setCharLevel("45");
            char3.setUserEntity(user3);
            char3.setGameEntity(game3);
            char3.setGuildEntity(guild3);
            char3.setCreatedBy("System");
            char3.setTimestamp(LocalDateTime.now());

            GameCharEntity char4 = new GameCharEntity();
            char4.setCharName("Frodo");
            char4.setCharLevel("30");
            char4.setUserEntity(user4);
            char4.setGameEntity(game4);
            char4.setGuildEntity(guild4);
            char4.setCreatedBy("System");
            char4.setTimestamp(LocalDateTime.now());

            GameCharEntity char5 = new GameCharEntity();
            char5.setCharName("Samwise");
            char5.setCharLevel("30");
            char5.setUserEntity(user5);
            char5.setGameEntity(game1);
            char5.setGuildEntity(guild1);
            char5.setCreatedBy("System");
            char5.setTimestamp(LocalDateTime.now());

            gameCharRepository.saveAll(List.of(char1, char2, char3, char4, char5));
        }
    }
}
