package com.guildify.guildify.service;

import com.guildify.guildify.model.PostCommentsEntity;
import com.guildify.guildify.model.dto.PostCommentRequest;
import com.guildify.guildify.model.dto.PostCommentResponse;
import com.guildify.guildify.repository.PostCommentsRepository;
import com.guildify.guildify.repository.PostRepository;
import com.guildify.guildify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostCommentService {
    @Autowired
    private PostCommentsRepository postCommentsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    public PostCommentResponse createNewPostComment(PostCommentRequest postCommentRequest){
        PostCommentsEntity postCommentsEntity = PostCommentsEntity.builder()
                .commentContent(postCommentRequest.getCommentContent())
                .userEntity(userRepository.findUserEntityByUserId(postCommentRequest.getUserId()))
                .postEntity(postRepository.findPostEntityByPostId(postCommentRequest.getPostId()))
                .build();
        postCommentsEntity.setCreatedBy(postCommentsEntity.getUserEntity().getDisplayName());
        postCommentsEntity.setTimestamp(LocalDateTime.now());
        //Persistance to DB
        postCommentsEntity = postCommentsRepository.save(postCommentsEntity);
        //Response Building...
        PostCommentResponse postCommentResponse = PostCommentResponse.builder()
                .commentContent(postCommentsEntity.getCommentContent())
                .senderDisplayName(postCommentsEntity.getUserEntity().getDisplayName())
                .timestamp(postCommentsEntity.getTimestamp())
                .postId(postCommentsEntity.getPostEntity().getPostId())
                .build();
        postCommentResponse.setCreatedAt(LocalDateTime.now());
        postCommentResponse.setCreatedBy(postCommentResponse.getCreatedBy());
        return postCommentResponse;
    }
    public List<PostCommentResponse> getAllPostCommentsOfPost(int postId){
        List<PostCommentsEntity> allPostCommentsOfPost = postCommentsRepository
                .findPostCommentsEntitiesByPostEntity_PostId(postId);
        List<PostCommentResponse> allPostCommentsOfPostAsResponse = new ArrayList<>();
        for(PostCommentsEntity postCommentsEntity : allPostCommentsOfPost){
            PostCommentResponse postCommentResponse = PostCommentResponse.builder()
                    .commentContent(postCommentsEntity.getCommentContent())
                    .senderDisplayName(postCommentsEntity.getUserEntity().getDisplayName())
                    .timestamp(postCommentsEntity.getTimestamp())
                    .postId(postCommentsEntity.getPostEntity().getPostId())
                    .build();
            postCommentResponse.setCreatedBy(postCommentsEntity.getCreatedBy());
            postCommentResponse.setCreatedAt(LocalDateTime.now());

            allPostCommentsOfPostAsResponse.add(postCommentResponse);
        }
        return allPostCommentsOfPostAsResponse;
    }
    public PostCommentResponse getSpecificPostComment(int commentId){
        PostCommentsEntity postCommentsEntity = postCommentsRepository.findPostCommentsEntityByCommentId(commentId);
        PostCommentResponse postCommentResponse = PostCommentResponse.builder()
                .commentContent(postCommentsEntity.getCommentContent())
                .senderDisplayName(postCommentsEntity.getUserEntity().getDisplayName())
                .timestamp(postCommentsEntity.getTimestamp())
                .postId(postCommentsEntity.getPostEntity().getPostId())
                .build();
        postCommentResponse.setCreatedBy(postCommentsEntity.getCreatedBy());
        postCommentResponse.setCreatedAt(LocalDateTime.now());
        return postCommentResponse;
    }
    public List<PostCommentResponse> getAllPostCommentsOfUser(int userId){
        List<PostCommentsEntity> allPostCommentsOfPost = postCommentsRepository
                .findPostCommentsEntitiesByUserEntity_UserId(userId);
        List<PostCommentResponse> allPostCommentsOfUserAsResponse = new ArrayList<>();
        for(PostCommentsEntity postCommentsEntity : allPostCommentsOfPost){
            PostCommentResponse postCommentResponse = PostCommentResponse.builder()
                    .commentContent(postCommentsEntity.getCommentContent())
                    .senderDisplayName(postCommentsEntity.getUserEntity().getDisplayName())
                    .timestamp(postCommentsEntity.getTimestamp())
                    .postId(postCommentsEntity.getPostEntity().getPostId())
                    .build();
            postCommentResponse.setCreatedBy(postCommentsEntity.getCreatedBy());
            postCommentResponse.setCreatedAt(LocalDateTime.now());

            allPostCommentsOfUserAsResponse.add(postCommentResponse);
        }
        return allPostCommentsOfUserAsResponse;
    }

    public void deletePostComment(int commentId){
        postCommentsRepository.delete(postCommentsRepository.findPostCommentsEntityByCommentId(commentId));
    }
}
