package com.example.firstapp.api.dto;

import com.google.gson.annotations.SerializedName;

public class FacultyDto {

    @SerializedName("_id")
    private String id;

    @SerializedName("userId")
    private StudentDto.UserDetails userId;

    @SerializedName("departmentId")
    private com.google.gson.JsonElement departmentId;

    @SerializedName("department")
    private DepartmentDto department;

    @SerializedName("specialization")
    private String specialization;

    @SerializedName("designation")
    private String designation;

    public String getId() { return id; }
    public StudentDto.UserDetails getUserId() { return userId; }
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
    public String getSpecialization() { return specialization; }
    public String getDesignation() { return designation; }

    public String getName() {
        return userId != null ? userId.name : null;
    }

    public String getEmail() {
        return userId != null ? userId.email : null;
    }
}
