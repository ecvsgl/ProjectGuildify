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
public class PostCommentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentID", nullable = false)
    private int commentId;
    private int commentQueueNumber;
    private String commentContent;
    private LocalDateTime commentTimestamp;

    @ManyToOne
    private UserEntity userEntity;

    @ManyToOne
    private PostEntity postEntity;

    @Override
    public String toString() {
        return "PostCommentsEntity{" +
                "commentId=" + commentId +
                ", commentQueueNumber=" + commentQueueNumber +
                ", commentContent='" + commentContent + '\'' +
                ", commentTimestamp=" + commentTimestamp +
                '}';
    }
}
