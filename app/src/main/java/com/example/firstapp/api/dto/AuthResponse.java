package com.example.firstapp.api.dto;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {

    @SerializedName("accessToken")
    private String accessToken;

    @SerializedName("refreshToken")
    private String refreshToken;

    @SerializedName("user")
    private UserData user;

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public UserData getUser() {
        return user;
    }

    public static class UserData {
        @SerializedName("id")
        private String id;

        @SerializedName("name")
        private String name;

        @SerializedName("email")
        private String email;

        @SerializedName("role")
        private String role;

        @SerializedName("status")
        private String status;

        @SerializedName("departmentId")
        private com.google.gson.JsonElement departmentId;

        @SerializedName("student")
        private StudentDto student;

        @SerializedName("firstName")
        private String firstName;

        @SerializedName("lastName")
        private String lastName;

        public String getId() { return id; }
        public String getName() {
            if (name != null) return name;
            if (firstName != null) return firstName + (lastName != null ? " " + lastName : "");
            return null;
        }
        public String getEmail() { return email; }
        public String getRole() { return role; }
        public String getStatus() { return status; }
        public String getDepartmentId() {
            if (departmentId == null || departmentId.isJsonNull()) return null;
            if (departmentId.isJsonPrimitive()) return departmentId.getAsString();
            if (departmentId.isJsonObject()) {
                com.google.gson.JsonElement idElem = departmentId.getAsJsonObject().get("_id");
                if (idElem != null) return idElem.getAsString();
            }
            return null;
        }
        public StudentDto getStudent() { return student; }
    }

    public static class Department {
        @SerializedName("_id")
        private String id;

        @SerializedName("name")
        private String name;

        @SerializedName("code")
        private String code;

        public String getId() { return id; }
        public String getName() { return name; }
        public String getCode() { return code; }
    }
}
