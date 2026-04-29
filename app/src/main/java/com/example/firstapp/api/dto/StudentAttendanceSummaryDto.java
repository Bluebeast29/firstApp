package com.example.firstapp.api.dto;

import com.google.gson.annotations.SerializedName;

public class StudentAttendanceSummaryDto {

    @SerializedName("studentId")
    private String studentId;

    @SerializedName("termId")
    private String termId;

    @SerializedName("totalSessions")
    private int totalSessions;

    @SerializedName("present")
    private int present;

    @SerializedName("absent")
    private int absent;

    @SerializedName("late")
    private int late;

    @SerializedName("excused")
    private int excused;

    @SerializedName("marked")
    private int marked;

    @SerializedName("unmarked")
    private int unmarked;

    @SerializedName("percentage")
    private double percentage;

    @SerializedName("byCourse")
    private java.util.List<CourseAttendanceSummary> byCourse;

    public String getStudentId() { return studentId; }
    public String getTermId() { return termId; }
    public int getTotalSessions() { return totalSessions; }
    public int getPresent() { return present; }
    public int getAbsent() { return absent; }
    public int getLate() { return late; }
    public int getExcused() { return excused; }
    public int getMarked() { return marked; }
    public int getUnmarked() { return unmarked; }
    public double getPercentage() { return percentage; }
    public java.util.List<CourseAttendanceSummary> getByCourse() { return byCourse; }

    public static class CourseAttendanceSummary {
        @SerializedName("courseId")
        private String courseId;

        @SerializedName("courseName")
        private String courseName;

        @SerializedName("courseCode")
        private String courseCode;

        @SerializedName("totalSessions")
        private int totalSessions;

        @SerializedName("present")
        private int present;

        @SerializedName("absent")
        private int absent;

        @SerializedName("percentage")
        private double percentage;

        public String getCourseId() { return courseId; }
        public String getCourseName() { return courseName; }
        public String getCourseCode() { return courseCode; }
        public int getTotalSessions() { return totalSessions; }
        public int getPresent() { return present; }
        public int getAbsent() { return absent; }
        public double getPercentage() { return percentage; }
    }
}
