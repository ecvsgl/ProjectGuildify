package com.guildify.guildify.repository;

import com.guildify.guildify.model.PostCommentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentsRepository extends JpaRepository<PostCommentsEntity, Integer> {
    PostCommentsEntity findPostCommentsEntityByCommentId(int commentId);
    List<PostCommentsEntity> findPostCommentsEntitiesByPostEntity_PostId(int postId);
    List<PostCommentsEntity> findPostCommentsEntitiesByUserEntity_UserId(int userId);

}
