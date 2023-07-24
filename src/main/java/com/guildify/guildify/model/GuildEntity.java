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
public class GuildEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guildID", nullable = false)
    private int guildId;
    private String guildName;

    @ManyToOne
    private GameEntity gameEntity;

    @OneToMany(mappedBy = "guildEntity")
    private List<GameCharEntity> gameCharEntityList = new ArrayList<>();

    @Override
    public String toString() {
        return "GuildEntity{" +
                "guildId=" + guildId +
                ", guildName='" + guildName + '\'' +
                '}';
    }
}
