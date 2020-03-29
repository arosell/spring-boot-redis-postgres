package com.example.caching.service;

import com.example.caching.model.Post;
import com.example.caching.repository.PostRepository;
import com.example.caching.exception.PostNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PostService {
    @Autowired
    PostRepository posts;

    /**
     * Saves a new post to database
     * @param post
     * @return Post
     */
    public Post createPost(final Post post) {
        posts.save(post);
        return post;
    }

    /**
     * Updates the details of a given post in database
     * @param post
     * @return Post
     * @throws PostNotFoundException
     */
    public Post updatePost(final Post post) throws PostNotFoundException {
        this.deletePost(post.getId());
        return this.createPost(post);
    }

    /**
     * Delete the post with the given ID from database
     * 
     * @param postId
     * @return post
     * @throws PostNotFoundException
     */
    public Post deletePost(final String postId) throws PostNotFoundException {
        Post post = posts.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
        posts.delete(post);
        return post;
    }

    /**
     * Loads single post from database
     * 
     * @param postId
     * @throws PostNotFoundException
     */
    public Post getPostByID(final String postId) throws PostNotFoundException {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
        return posts.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
    }

    /**
     * Loads all posts from database
     */
    public Iterable<Post> getAllPosts() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
        return posts.findAll();
    }
}
