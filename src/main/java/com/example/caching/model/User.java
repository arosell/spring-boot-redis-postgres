package com.example.caching.model;

import javax.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "users", schema="postservice")
public class User implements Serializable {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "fullname")
    private String fullname;

    private static final long serialVersionUID = -5766207474404481797L;

    public User() {
    }

    public User(String username, String name) {
        this.username = username;
        this.fullname = name;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getName() {
        return fullname;
    }

    public void setName(String fullname) {
        this.fullname = fullname;
    }
}
