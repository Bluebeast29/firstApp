package com.example.firstapp.api.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourseDto {

    @SerializedName("_id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("code")
    private String code;

    @SerializedName("description")
    private String description;

    @SerializedName("credits")
    private int credits;

    @SerializedName("departmentId")
    private com.google.gson.JsonElement departmentId;

    @SerializedName("department")
    private DepartmentDto department;

    @SerializedName("elective")
    private boolean elective;

    @SerializedName("level")
    private String level;

    @SerializedName("prerequisites")
    private List<String> prerequisites;

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCode() { return code; }
    public String getDescription() { return description; }
    public int getCredits() { return credits; }
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
    public boolean isElective() { return elective; }
    public String getLevel() { return level; }
    public List<String> getPrerequisites() { return prerequisites; }
}
