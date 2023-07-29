package com.guildify.guildify.repository;

import com.guildify.guildify.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    @Query("select u from UserEntity u where u.userId =: userId")
    UserEntity findUserByUserId(Integer userId);

    UserEntity findUserEntityByUsernameHash(String s);

    UserEntity findUserEntityByDisplayName(String s);
}
