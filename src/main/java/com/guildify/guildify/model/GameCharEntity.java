package com.guildify.guildify.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameCharEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "charID", nullable = false)
    private int charId;
    private String charName;
    private String charLevel;

    @ManyToOne
    private UserEntity userEntity;

    @ManyToOne
    private GameEntity gameEntity;

    @ManyToOne
    private GuildEntity guildEntity;

    @Override
    public String toString() {
        return "GameCharEntity{" +
                "charId=" + charId +
                ", charName='" + charName + '\'' +
                ", charLevel='" + charLevel + '\'' +
                '}';
    }
}
