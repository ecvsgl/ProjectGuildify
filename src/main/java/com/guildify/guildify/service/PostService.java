package com.guildify.guildify.service;

import com.guildify.guildify.model.PostCommentsEntity;
import com.guildify.guildify.model.PostEntity;
import com.guildify.guildify.model.UserEntity;
import com.guildify.guildify.model.dto.PostCommentResponse;
import com.guildify.guildify.model.dto.PostRequest;
import com.guildify.guildify.model.dto.PostResponse;
import com.guildify.guildify.repository.PostRepository;
import com.guildify.guildify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    public PostResponse createNewPostEntity(String jwt, PostRequest postRequest){
        PostEntity postEntity = PostEntity.builder()
                .postContent(postRequest.getPostContent())
                .userEntity(jwtUserEntityExtractor(jwt))
                .postCommentsEntityList(null)
                .build();
        postEntity.setTimestamp(LocalDateTime.now());
        postEntity.setCreatedBy(jwtUserEntityExtractor(jwt).getDisplayName());
        postEntity = postRepository.save(postEntity);
        return postEntityToPostResponseMapper(jwt, postEntity);
    }
    public List<PostResponse> getAllPosts(String jwt){
        List <PostResponse> allPostsToPostResponse = new ArrayList<>();
        for(PostEntity postEntity: postRepository.findAll()){
            allPostsToPostResponse.add(postEntityToPostResponseMapper(jwt,postEntity));
        }
        return allPostsToPostResponse;
    }
    public List<PostResponse> getAllPostsOfAUserEntity(String jwt,String userDisplayName){
        List <PostResponse> allPostsToPostResponse = new ArrayList<>();
        for(PostEntity postEntity: postRepository.findPostEntitiesByUserEntity_DisplayName(userDisplayName)){
            allPostsToPostResponse.add(postEntityToPostResponseMapper(jwt,postEntity));
        }
        return allPostsToPostResponse;
    }
    public PostResponse getPostByPostId(String jwt, int postId){
        return postEntityToPostResponseMapper(jwt,postRepository.findPostEntityByPostId(postId));
    }
    public String deletePostEntity(String jwt, int postId){
        if(postRepository.existsById(postId)){
            PostEntity postEntity = postRepository.findPostEntityByPostId(postId);
            if (jwtUserEntityExtractor(jwt) == postEntity.getUserEntity()){
                postRepository.delete(postRepository.findPostEntityByPostId(postId));
                return "Post Deleted Successfully.";
            }
            return "You cannot delete a post of another user.";
        }
        return "No such post is found.";
    }
    public String deletePostEntityAsAdmin(int postId){
        if(postRepository.existsById(postId)){
            PostEntity postEntity = postRepository.findPostEntityByPostId(postId);
            postRepository.delete(postEntity);
            return "Post deletion successful.";
        }
        return "No such post is found.";
    }
    public PostResponse postEntityToPostResponseMapper(String jwt, PostEntity postEntity){
        PostResponse postResponse = PostResponse.builder()
                .postId(postEntity.getPostId())
                .postContent(postEntity.getPostContent())
                .postOwnerDisplayName(postEntity.getUserEntity().getDisplayName())
                .build();
        if (postEntity.getPostCommentsEntityList() != null) {
            List<PostCommentResponse> postComments = new ArrayList<>();
            for(PostCommentsEntity x : postEntity.getPostCommentsEntityList()){
                PostCommentResponse postCommentResponse = PostCommentResponse.builder()
                        .commentId(x.getCommentId())
                        .commentContent(x.getCommentContent())
                        .senderDisplayName(x.getUserEntity().getDisplayName())
                        .postId(x.getPostEntity().getPostId())
                        .build();
                postCommentResponse.setCreatedAt(LocalDateTime.now());
                postCommentResponse.setCreatedBy(jwtUserEntityExtractor(jwt).getDisplayName());
                postComments.add(postCommentResponse);
            }
            postResponse.setPostCommentsList(postComments);
        } else {
            postResponse.setPostCommentsList(null);
        }
        postResponse.setCreatedAt(LocalDateTime.now());
        postResponse.setCreatedBy(jwtUserEntityExtractor(jwt).getDisplayName());
        return postResponse;
    }

    public UserEntity jwtUserEntityExtractor(String jwt){
        Map<String, Object> claims = tokenService.decodeJwt(jwt).getClaims();
        return userRepository.findUserEntityByUsername((String)claims.get("sub"));
    }
}
