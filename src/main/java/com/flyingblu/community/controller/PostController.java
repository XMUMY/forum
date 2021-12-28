package com.flyingblu.community.controller;

import com.flyingblu.community.model.Post;
import com.flyingblu.community.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("insert")
    public String insert() {
        Post post = new Post();
        post.setBody("Hello world!");
        post.setTitle("Anybody?");
        post.setCommunityId(1);
        postService.insert(post);
        return post.getId().toString();
    }

    @GetMapping("get")
    public Post get() {
        Post post = postService.getById(1);
        System.out.println(post);
        return post;
    }

    @GetMapping("get-all")
    public List<Post> getAll() {
        return postService.getAll();
    }
}
