package com.guildify.guildify.model;

import com.fasterxml.jackson.databind.ser.Serializers;
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
public class GuildEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guildID", nullable = false)
    private int guildId;
    private String guildName;

    @OneToOne
    private UserEntity guildLeaderUserEntity;

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
