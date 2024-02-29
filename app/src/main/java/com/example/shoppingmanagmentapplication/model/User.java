package com.example.shoppingmanagmentapplication.model;

import java.util.Objects;

public class User {
    private String username;
    private String email;
    private String uid;
    private int age;

    public User() {
        this.age = 10;
    }


    public User(String username, String email)
    {
        this.username = username;
        this.email = email;
        this.age = 10;
    }


    public String getUsername() {
        return username;
    }
    public String getUid() {return uid;}

    public void setUid(String uid) {this.uid = uid;}

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email);
    }
}
