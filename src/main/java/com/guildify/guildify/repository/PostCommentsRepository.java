package com.guildify.guildify.repository;

import com.guildify.guildify.model.PostCommentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCommentsRepository extends JpaRepository<PostCommentsEntity, Integer> {
}
