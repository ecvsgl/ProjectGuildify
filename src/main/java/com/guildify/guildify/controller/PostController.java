package com.guildify.guildify.controller;

import com.guildify.guildify.model.dto.PostRequest;
import com.guildify.guildify.model.dto.PostResponse;
import com.guildify.guildify.service.PostService;
import com.guildify.guildify.utility.StaticMethods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PostController {
    @Autowired
    private PostService postService;
    @PostMapping("/user/newpost")
    public PostResponse createNewPostEntity(@RequestHeader("Authorization") String bearerToken,
                                            @RequestBody PostRequest postRequest){
        return postService.createNewPostEntity(StaticMethods.getJwtFromRequestHeader(bearerToken), postRequest);
    }
    @GetMapping("/user/allposts")
    public List<PostResponse> getAllPosts(@RequestHeader("Authorization") String bearerToken){
        return postService.getAllPosts(StaticMethods.getJwtFromRequestHeader(bearerToken));
    }
    @GetMapping("/admin/posts/{userDisplayName}")
    public List<PostResponse> getAllPostsOfAUserEntity(@RequestHeader("Authorization") String bearerToken,
                                                       @PathVariable String userDisplayName){
        return postService.getAllPostsOfAUserEntity(StaticMethods.getJwtFromRequestHeader(bearerToken),userDisplayName);
    }
    @GetMapping("/admin/post/{postId}")
    public PostResponse getPostByPostId(@RequestHeader("Authorization") String bearerToken,
                                        @PathVariable int postId){
        return postService.getPostByPostId(StaticMethods.getJwtFromRequestHeader(bearerToken), postId);
    }
    @DeleteMapping("/user/post/{postId}")
    public String deletePostEntity(@RequestHeader("Authorization") String bearerToken,
                                   @PathVariable int postId){
        return postService.deletePostEntity(StaticMethods.getJwtFromRequestHeader(bearerToken),postId);
    }

    @DeleteMapping("/admin/post/{postId}")
    public String deletePostEntityAsAdmin(@PathVariable int postId){
        return postService.deletePostEntityAsAdmin
                (postId);
    }

}
