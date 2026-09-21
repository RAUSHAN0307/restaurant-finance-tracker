package com.intellect.financeTracker.dto;

public class UserRegisterRequest {

    private String username;
    private String password;
    private String email;
    private String role;

    // Default Constructor
    public UserRegisterRequest() {
    }

    // Parameterized Constructor
    public UserRegisterRequest(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserRegisterRequest [username=" + username + ", email=" + email + "]";
    }
}