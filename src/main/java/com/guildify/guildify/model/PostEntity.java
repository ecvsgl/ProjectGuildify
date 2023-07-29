package com.guildify.guildify.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postID", nullable = false)
    private int postId;
    private String postContent;
    private LocalDateTime postTimestamp;

    @ManyToOne
    private UserEntity userEntity;

    @OneToMany(mappedBy = "postEntity")
    private List<PostCommentsEntity> postCommentsEntityList = new ArrayList<>();

    @Override
    public String toString() {
        return "PostEntity{" +
                "postId=" + postId +
                ", postContent='" + postContent + '\'' +
                ", postDate=" + postTimestamp +
                '}';
    }
}
