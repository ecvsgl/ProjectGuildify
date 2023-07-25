package com.guildify.guildify.repository;

import com.guildify.guildify.model.GameCharEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameCharRepository extends JpaRepository<GameCharEntity, Integer> {
}
