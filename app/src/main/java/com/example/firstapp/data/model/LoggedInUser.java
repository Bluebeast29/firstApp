package com.example.firstapp.data.model;

public class LoggedInUser {

    private String userId;
    private String displayName;
    private String email;
    private String role;
    private String accessToken;
    private String refreshToken;

    public LoggedInUser(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public LoggedInUser(String userId, String displayName, String email, String role,
                        String accessToken, String refreshToken) {
        this.userId = userId;
        this.displayName = displayName;
        this.email = email;
        this.role = role;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
