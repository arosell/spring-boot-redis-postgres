package com.example.caching.model;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts", schema="postservice")
public class Post implements Serializable {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JoinColumn(name = "created_user")
    private User user;

    @Column(name = "created_date")
    private LocalDateTime date;

    private static final long serialVersionUID = -5128506251041559131L;

    public Post() {
    }

    public Post(String id, String message, User user, LocalDateTime date) {
        this.id = id;
        this.message = message;
        this.user = user;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
