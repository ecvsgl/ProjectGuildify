package com.guildify.guildify.controller;

import com.guildify.guildify.model.dto.PostRequest;
import com.guildify.guildify.model.dto.PostResponse;
import com.guildify.guildify.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PostController {
    @Autowired
    private PostService postService;
    @PostMapping("/post")
    public PostResponse createNewPostEntity(@RequestBody PostRequest postRequest){
        return postService.createNewPostEntity(postRequest);
    }
    @GetMapping("/posts")
    public List<PostResponse> getAllPosts(){
        return postService.getAllPosts();
    }
    @GetMapping("/posts/{userId}")
    public List<PostResponse> getAllPostsOfAUserEntity(@PathVariable int userId){
        return postService.getAllPostsOfAUserEntity(userId);
    }
    @GetMapping("/post/{postId}")
    public PostResponse getPostByPostId(@PathVariable int postId){
        return postService.getPostByPostId(postId);
    }
    @DeleteMapping("/post/{postId}")
    public String deletePostEntity(@PathVariable int postId){
        postService.deletePostEntity(postId);
        return "Post Deleted Successfully";
    }

}
