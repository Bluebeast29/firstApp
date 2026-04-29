package com.example.firstapp.api.dto;

import com.google.gson.annotations.SerializedName;

public class StudentDto {

    @SerializedName("_id")
    private String id;

    @SerializedName("userId")
    private UserDetails userId;

    @SerializedName("rollNumber")
    private String rollNumber;

    @SerializedName("departmentId")
    private com.google.gson.JsonElement departmentId;

    @SerializedName("department")
    private DepartmentDto department;

    @SerializedName("batch")
    private String batch;

    @SerializedName("semester")
    private int semester;

    public String getId() { return id; }
    public UserDetails getUserId() { return userId; }
    public String getRollNumber() { return rollNumber; }

    public String getDepartmentId() {
        if (departmentId == null || departmentId.isJsonNull()) return null;
        if (departmentId.isJsonPrimitive()) return departmentId.getAsString();
        if (departmentId.isJsonObject()) {
            com.google.gson.JsonElement idElem = departmentId.getAsJsonObject().get("_id");
            if (idElem != null) return idElem.getAsString();
        }
        return null;
    }

    public DepartmentDto getDepartment() {
        if (department != null) return department;
        if (departmentId != null && departmentId.isJsonObject()) {
            return new com.google.gson.Gson().fromJson(departmentId, DepartmentDto.class);
        }
        return null;
    }

    public String getBatch() { return batch; }
    public int getSemester() { return semester; }

    public String getName() {
        return userId != null ? userId.name : null;
    }

    public String getEmail() {
        return userId != null ? userId.email : null;
    }

    public static class UserDetails {
        @SerializedName("_id")
        public String id;
        @SerializedName("name")
        public String name;
        @SerializedName("email")
        public String email;
    }
}
