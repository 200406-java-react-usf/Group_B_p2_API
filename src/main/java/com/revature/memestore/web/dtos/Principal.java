package com.revature.memestore.web.dtos;

import com.revature.memestore.models.User;
import com.revature.memestore.models.UserRole;

import java.util.Objects;

public class Principal {

    private int user_id;
    private String username;
    private UserRole role;

    public Principal() {
    }

    public Principal(int id, String username, UserRole role) {
        this.user_id = id;
        this.username = username;
        this.role = role;
    }

    public Principal(User u){

        this.user_id = u.getUser_id();
        this.username = u.getUsername();
        this.role = u.getRole_id();

    }

    public int getId() {
        return user_id;
    }

    public void setId(int id) {
        this.user_id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Principal principal = (Principal) o;
        return user_id == principal.user_id &&
                Objects.equals(username, principal.username) &&
                role == principal.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, username, role);
    }

    @Override
    public String toString() {
        return "Principal{" +
                "id=" + user_id +
                ", username='" + username + '\'' +
                ", role=" + role +
                '}';
    }
}
