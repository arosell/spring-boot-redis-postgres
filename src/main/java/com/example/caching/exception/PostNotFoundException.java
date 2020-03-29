package com.example.caching.exception;

public class PostNotFoundException extends Exception {
    private static final long serialVersionUID = -2909128808469660372L;

    public PostNotFoundException(String postId) {
        super("Cannot find post with id:" + postId);
    }
}
