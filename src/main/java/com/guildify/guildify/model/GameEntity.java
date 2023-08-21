package com.guildify.guildify.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gameID", nullable = false)
    private int gameId;
    @Column(nullable = false, unique = true)
    private String gameName;
    private String gamePublisher;

    @OneToMany(mappedBy = "gameEntity")
    private List<GameCharEntity> gameCharEntityList = new ArrayList<>();

    @Override
    public String toString() {
        return "GameEntity{" +
                "gameId=" + gameId +
                ", gameName='" + gameName + '\'' +
                ", gamePublisher='" + gamePublisher + '\'' +
                '}';
    }
}
