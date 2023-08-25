package com.guildify.guildify.controller;

import com.guildify.guildify.model.dto.PostCommentRequest;
import com.guildify.guildify.model.dto.PostCommentResponse;
import com.guildify.guildify.service.PostCommentService;
import com.guildify.guildify.utility.StaticMethods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PostCommentController {
    @Autowired
    private PostCommentService postCommentService;

    @PostMapping("/user/newcomment")
    public PostCommentResponse createNewPostComment(@RequestHeader("Authorization") String bearerToken,
                                                    @RequestBody PostCommentRequest postCommentRequest) {
        return postCommentService.createNewPostComment(StaticMethods.getJwtFromRequestHeader(bearerToken), postCommentRequest);
    }
    @GetMapping("/user/allcommentsofpost/{postId}")
    public List<PostCommentResponse> getAllPostCommentsOfPost(@RequestHeader("Authorization") String bearerToken,
                                                              @PathVariable int postId){
        return postCommentService.getAllPostCommentsOfPost(StaticMethods.getJwtFromRequestHeader(bearerToken), postId);
    }
    @GetMapping("/user/getcomment/{commentId}")
    public PostCommentResponse getSpecificPostComment(@RequestHeader("Authorization") String bearerToken,
                                                      @PathVariable int commentId){
        return postCommentService.getSpecificPostComment(StaticMethods.getJwtFromRequestHeader(bearerToken), commentId);
    }
    @GetMapping("/admin/mycomments/{userId}")
    public List<PostCommentResponse> getAllPostCommentsOfUser(@RequestHeader("Authorization") String bearerToken,
                                                              @PathVariable int userId){
        return postCommentService.getAllPostCommentsOfUser(StaticMethods.getJwtFromRequestHeader(bearerToken), userId);
    }
    @DeleteMapping("/user/deletecomment/{commentId}")
    public String deletePostComment(@RequestHeader("Authorization") String bearerToken,
                                    @PathVariable int commentId){
        return postCommentService.deletePostComment(StaticMethods.getJwtFromRequestHeader(bearerToken), commentId);
    }

    @DeleteMapping("/admin/deletecomment/{commentId}")
    public String deletePostCommentAsAdmin(@PathVariable int commentId){
        return postCommentService.deletePostCommentAsAdmin(commentId);
    }

}
