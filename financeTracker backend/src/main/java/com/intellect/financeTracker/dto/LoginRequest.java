package com.intellect.financeTracker.dto;

public class LoginRequest {
    private String username;
    private String password;

    // 1. Default (No-Args) Constructor
    public LoginRequest() {
    }

    // 2. Parameterized Constructor
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // 3. Getters and Setters
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

    // 4. ToString Method
    @Override
    public String toString() {
        return "LoginRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}