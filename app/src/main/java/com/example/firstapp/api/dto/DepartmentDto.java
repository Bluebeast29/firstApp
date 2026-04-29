package com.example.firstapp.api.dto;

import com.google.gson.annotations.SerializedName;

public class DepartmentDto {

    @SerializedName("_id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("code")
    private String code;

    @SerializedName("stats")
    private DepartmentStatsDto stats;

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCode() { return code; }
    public DepartmentStatsDto getStats() { return stats; }

    public static class DepartmentStatsDto {
        @SerializedName("studentCount")
        private Integer studentCount;

        @SerializedName("facultyCount")
        private Integer facultyCount;

        @SerializedName("courseCount")
        private Integer courseCount;

        public Integer getStudentCount() { return studentCount; }
        public Integer getFacultyCount() { return facultyCount; }
        public Integer getCourseCount() { return courseCount; }
    }
}
