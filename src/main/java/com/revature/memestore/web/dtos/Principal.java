package com.revature.memestore.web.dtos;

import com.revature.memestore.models.User;
import com.revature.memestore.models.UserRole;

import java.util.Objects;

public class Principal {

    private int user_id;
    private String username;
    private UserRole role;
    private String last_name;

    public Principal() {
    }

    public Principal(int user_id, String username, UserRole role, String last_name) {
        this.user_id = user_id;
        this.username = username;
        this.role = role;
        this.last_name = last_name;
    }

    public Principal(User u){

        this.user_id = u.getUser_id();
        this.username = u.getUsername();
        this.role = u.getRole_id();
        this.last_name = u.getLast_name();

    }

    public Principal(int user_id, String username, UserRole role) {
        this.user_id = user_id;
        this.username = username;
        this.role = role;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Principal principal = (Principal) o;
        return user_id == principal.user_id &&
                Objects.equals(username, principal.username) &&
                role == principal.role &&
                Objects.equals(last_name, principal.last_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, username, role, last_name);
    }

    @Override
    public String toString() {
        return "Principal{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", role=" + role +
                ", last_name='" + last_name + '\'' +
                '}';
    }
}
