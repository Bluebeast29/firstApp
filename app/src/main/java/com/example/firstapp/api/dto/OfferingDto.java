package com.example.firstapp.api.dto;

import com.google.gson.annotations.SerializedName;

public class OfferingDto {

    @SerializedName("_id")
    private String id;

    @SerializedName("courseId")
    private com.google.gson.JsonElement courseId;

    @SerializedName("termId")
    private com.google.gson.JsonElement termId;

    @SerializedName("facultyId")
    private com.google.gson.JsonElement facultyId;

    @SerializedName("section")
    private String section;

    @SerializedName("schedule")
    private Object schedule;

    @SerializedName("capacity")
    private int capacity;

    public String getId() { return id; }

    public CourseDto getCourseId() {
        if (courseId == null || courseId.isJsonNull()) return null;
        if (courseId.isJsonObject()) {
            return new com.google.gson.Gson().fromJson(courseId, CourseDto.class);
        }
        return null;
    }

    public com.google.gson.JsonElement getTermId() { return termId; }

    public FacultyDto getFacultyId() {
        if (facultyId == null || facultyId.isJsonNull()) return null;
        if (facultyId.isJsonObject()) {
            return new com.google.gson.Gson().fromJson(facultyId, FacultyDto.class);
        }
        return null;
    }

    public String getSection() { return section; }
    public Object getSchedule() { return schedule; }
    public int getCapacity() { return capacity; }

    public String getCourseName() {
        CourseDto course = getCourseId();
        return course != null ? course.getName() : null;
    }

    public String getCourseCode() {
        CourseDto course = getCourseId();
        return course != null ? course.getCode() : null;
    }

    public String getFacultyName() {
        FacultyDto faculty = getFacultyId();
        return faculty != null ? faculty.getName() : null;
    }
}
