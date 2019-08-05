package com.example.userservice.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    private String name;
    private String email;
    private String description;

    private User() {
    }

    public User(String name, String email, String description) {
        this.name = name;
        this.email = email;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }
}
