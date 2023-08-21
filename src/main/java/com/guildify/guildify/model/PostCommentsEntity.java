package com.guildify.guildify.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentsEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentID", nullable = false)
    private int commentId;
    @Column(nullable = false)
    private String commentContent;
    @ManyToOne
    private UserEntity userEntity;

    @ManyToOne
    private PostEntity postEntity;

    @Override
    public String toString() {
        return "PostCommentsEntity{" +
                "commentId=" + commentId +
                ", commentContent='" + commentContent + '\'' +
                '}';
    }
}
