package com.guildify.guildify.service;

import com.guildify.guildify.model.PostEntity;
import com.guildify.guildify.model.dto.PostRequest;
import com.guildify.guildify.model.dto.PostResponse;
import com.guildify.guildify.repository.PostRepository;
import com.guildify.guildify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public PostResponse createNewPostEntity(PostRequest postRequest){
        PostEntity postEntity = PostEntity.builder()
                .postContent(postRequest.getPostContent())
                .userEntity(userRepository.findUserEntityByUserId(postRequest.getUserId()))
                .postCommentsEntityList(null)
                .build();
        postEntity.setTimestamp(LocalDateTime.now());
        postEntity.setCreatedBy(
                userRepository.findUserEntityByUserId(postRequest.getUserId()).getDisplayName());
        postEntity = postRepository.save(postEntity);
        //Response Building ...
        PostResponse postResponse = PostResponse.builder()
                .postContent(postEntity.getPostContent())
                .postTimestamp((postEntity.getTimestamp()))
                .postOwnerName(postEntity.getUserEntity().getDisplayName())
                .build();
        postResponse.setCreatedAt(postEntity.getTimestamp());
        postResponse.setCreatedBy(postEntity.getUserEntity().getDisplayName());
        return postResponse;
    }
    public List<PostResponse> getAllPostsOfAUserEntity(int userId){
        List<PostEntity> allPostsByUser = postRepository.findPostEntitiesByUserEntity_UserId(userId);
        List <PostResponse> allPostsToPostResponse = new ArrayList<>();
        for(PostEntity postEntity: allPostsByUser){
            PostResponse postResponse = PostResponse.builder()
                    .postContent(postEntity.getPostContent())
                    .postTimestamp(postEntity.getTimestamp())
                    .postOwnerName(postEntity.getUserEntity().getDisplayName())
                    .build();
            postResponse.setCreatedBy(postResponse.getPostOwnerName());
            postResponse.setCreatedAt(postResponse.getPostTimestamp());

            allPostsToPostResponse.add(postResponse);
        }
        return allPostsToPostResponse;
    }
    public PostResponse getPostEntityByPostId(int postId){
        PostEntity postEntity = postRepository.findPostEntitiesByPostId(postId);
        PostResponse postResponse = PostResponse.builder()
                .postContent(postEntity.getPostContent())
                .postTimestamp(postEntity.getTimestamp())
                .postOwnerName(postEntity.getUserEntity().getDisplayName())
                .build();
        return postResponse;
    }
    public void deletePostEntity(int postId){
        postRepository.delete(postRepository.findPostEntitiesByPostId(postId));
    }
}
