package com.guildify.guildify.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "userID", nullable = false)
    private Integer userId;
    private String usernameHash;
    private String passwordHash;
    private String displayName;
    private String displayNameHash;
    private String email;
    private String accountRank;
    private LocalDateTime accountCreationDate;

    @OneToMany(mappedBy = "userEntity")
    private List<PostEntity> postEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity")
    private List<PostCommentsEntity> postCommentsEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity")
    private List<GameCharEntity> gameCharEntityList = new ArrayList<>();

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", usernameHash='" + usernameHash + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", displayName='" + displayName + '\'' +
                ", displayNameHash='" + displayNameHash + '\'' +
                ", email='" + email + '\'' +
                ", accountRank='" + accountRank + '\'' +
                ", accountCreationDate=" + accountCreationDate +
                '}';
    }
}

