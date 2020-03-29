package com.example.caching.controller;

import com.example.caching.exception.PostNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class PostExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(PostExceptionHandler.class);

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Post Not Found")
    public void handlePostNotFound(final HttpServletRequest request, final Exception ex) {
        log.error("{} : {}", ex.getMessage(), request.getRequestURI());
    }
}
