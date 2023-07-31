package com.guildify.guildify.controller;

import com.guildify.guildify.model.dto.PostCommentRequest;
import com.guildify.guildify.model.dto.PostCommentResponse;
import com.guildify.guildify.service.PostCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PostCommentController {
    @Autowired
    private PostCommentService postCommentService;

    @PostMapping("/comment")
    public PostCommentResponse createNewPostComment(@RequestBody PostCommentRequest postCommentRequest) {
        return postCommentService.createNewPostComment(postCommentRequest);
    }
    @GetMapping("/comments/{postId}")
    public List<PostCommentResponse> getAllPostCommentsOfPost(@PathVariable int postId){
        return postCommentService.getAllPostCommentsOfPost(postId);
    }
    @GetMapping("/comment/{commentId}")
    public PostCommentResponse getSpecificPostComment(@PathVariable int commentId){
        return postCommentService.getSpecificPostComment(commentId);
    }
    @GetMapping("/mycomments/{userId}")
    public List<PostCommentResponse> getAllPostCommentsOfUser(@PathVariable int userId){
        return postCommentService.getAllPostCommentsOfUser(userId);
    }
    @DeleteMapping("/comment/{commentId}")
    public String deletePostComment(@PathVariable int commentId){
        postCommentService.deletePostComment(commentId);
        return "Comment Deleted Successfully.";
    }


}
