package com.example.caching.controller;

import com.example.caching.exception.PostNotFoundException;
import com.example.caching.model.Post;
import com.example.caching.service.PostService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    private static final Logger log = LoggerFactory.getLogger(PostController.class);

    @GetMapping("/{id}")
    @Cacheable(value = "posts", key = "#id")
    public Post getPostByID(@PathVariable final String id) throws PostNotFoundException {
        log.info("Get post with id {}", id);
        return postService.getPostByID(id);
    }

    @PostMapping("/create")
    @CachePut(value = "posts", key = "#post.getId()")
    @CacheEvict(value = "allPosts", allEntries = true)
    public Post createPost(@RequestBody final Post post) {
        log.info("Create post with id {}", post.getId());
        return postService.createPost(post);
    }

    @PostMapping("/update")
    @CachePut(value = "posts", key = "#post.getId()")
    @CacheEvict(value = "allPosts", allEntries = true)
    public Post updatePostByID(@RequestBody final Post post) throws PostNotFoundException {
        log.info("Update post with id {}", post.getId());
        return postService.updatePost(post);
    }

    @DeleteMapping("/delete/{id}")
    @Caching(evict = { 
        @CacheEvict(value = "posts", key = "#id"), 
        @CacheEvict(value = "allPosts", allEntries = true)
    })
    public Post deletePostByID(@PathVariable final String id) throws PostNotFoundException {
        log.info("Delete post with id {}", id);
        return postService.deletePost(id);
    }

    @GetMapping("/all")
    @Cacheable(value = "allPosts")
    public Iterable<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/clearCache")
    @Caching(evict = { 
        @CacheEvict(value = "posts", allEntries = true), 
        @CacheEvict(value = "allPosts", allEntries = true)
    })
    public String clearCache() {
        log.info("Clear caches");
        return "All caches cleared";
    }
}
