package com.guildify.guildify.service;

import com.guildify.guildify.model.PostCommentsEntity;
import com.guildify.guildify.model.UserEntity;
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
import java.util.Map;

@Service
public class PostCommentService {
    @Autowired
    private PostCommentsRepository postCommentsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TokenService tokenService;

    public PostCommentResponse createNewPostComment(String jwt, PostCommentRequest postCommentRequest){
        if(postRepository.findPostEntityByPostId(postCommentRequest.getPostId())==null){
            throw new IllegalArgumentException("A comment must be related to a post itself.");
        }
        PostCommentsEntity postCommentsEntity = PostCommentsEntity.builder()
                .commentContent(postCommentRequest.getCommentContent())
                .userEntity(jwtUserEntityExtractor(jwt))
                .postEntity(postRepository.findPostEntityByPostId(postCommentRequest.getPostId()))
                .build();
        postCommentsEntity.setCreatedBy(jwtUserEntityExtractor(jwt).getDisplayName());
        postCommentsEntity.setTimestamp(LocalDateTime.now());
        //Persistance to DB
        postCommentsEntity = postCommentsRepository.save(postCommentsEntity);
        //Response Building...
        return postCommentEntityToResponseMapper(jwt,postCommentsEntity);
    }
    public List<PostCommentResponse> getAllPostCommentsOfPost(String jwt, int postId){
        if(!postCommentsRepository.existsById(postId)){
            return null;
        }
        List<PostCommentsEntity> allPostCommentsOfPost = postCommentsRepository
                .findPostCommentsEntitiesByPostEntity_PostId(postId);

        List<PostCommentResponse> allPostCommentsOfPostAsResponse = new ArrayList<>();
        for(PostCommentsEntity postCommentsEntity : allPostCommentsOfPost){
            allPostCommentsOfPostAsResponse.add(postCommentEntityToResponseMapper(jwt,postCommentsEntity));
        }
        return allPostCommentsOfPostAsResponse;
    }
    public PostCommentResponse getSpecificPostComment(String jwt, int commentId){
        if(!postCommentsRepository.existsById(commentId)){
            return null;
        }
        return postCommentEntityToResponseMapper(jwt, postCommentsRepository.findPostCommentsEntityByCommentId(commentId)) ;
    }
    public List<PostCommentResponse> getAllPostCommentsOfUser(String jwt, int userId){
        List<PostCommentsEntity> allPostCommentsOfPost = postCommentsRepository
                .findPostCommentsEntitiesByUserEntity_UserId(userId);
        if(allPostCommentsOfPost==null){
            return null;
        }
        List<PostCommentResponse> allPostCommentsOfUserAsResponse = new ArrayList<>();
        for(PostCommentsEntity postCommentsEntity : allPostCommentsOfPost){
            allPostCommentsOfUserAsResponse.add(postCommentEntityToResponseMapper(jwt,postCommentsEntity));
        }
        return allPostCommentsOfUserAsResponse;
    }

    public String deletePostComment(String jwt, int commentId){
        if(!postCommentsRepository.existsById(commentId)){
            return "No such comment exists.";
        } else if (postCommentsRepository.findPostCommentsEntityByCommentId(commentId).getUserEntity() != jwtUserEntityExtractor(jwt)){
            return "You cannot delete someone else's comment.";
        }
        postCommentsRepository.delete(postCommentsRepository.findPostCommentsEntityByCommentId(commentId));
        return "Comment deleted successfully.";
    }
    public String deletePostCommentAsAdmin(int commentId){
        if(!postCommentsRepository.existsById(commentId)){
            return "No such comment exists.";
        }
        postCommentsRepository.delete(postCommentsRepository.findPostCommentsEntityByCommentId(commentId));
        return "Comment deleted successfully.";
    }

    public PostCommentResponse postCommentEntityToResponseMapper(String jwt, PostCommentsEntity postCommentsEntity){
        PostCommentResponse postCommentResponse = PostCommentResponse.builder()
              .commentId(postCommentsEntity.getCommentId())
              .commentContent(postCommentsEntity.getCommentContent())
              .senderDisplayName(null)
              .build();
        if(postCommentsEntity.getUserEntity()!=null){
          postCommentResponse.setSenderDisplayName(postCommentsEntity.getUserEntity().getDisplayName());
        }
        if(postCommentsEntity.getPostEntity()!=null){
            postCommentResponse.setPostId(postCommentsEntity.getPostEntity().getPostId());
        }
        postCommentResponse.setCreatedBy(jwtUserEntityExtractor(jwt).getDisplayName());
        postCommentResponse.setCreatedAt(LocalDateTime.now());
        return postCommentResponse;
    }

    public UserEntity jwtUserEntityExtractor(String jwt){
        Map<String, Object> claims = tokenService.decodeJwt(jwt).getClaims();
        return userRepository.findUserEntityByUsername((String)claims.get("sub"));
    }
}
