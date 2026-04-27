package com.example.firstapp.models;

import java.util.List;

public class Student {
    public String id;
    public String name;
    public String email;
    public String rollNumber;
    public String department;
    public String batch;
    public int semester;
    public List<String> enrolledCourses;
    public String overallAttendance;

    public Student(String id, String name, String email, String rollNumber, String department, String batch, int semester, List<String> enrolledCourses, String overallAttendance) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.rollNumber = rollNumber;
        this.department = department;
        this.batch = batch;
        this.semester = semester;
        this.enrolledCourses = enrolledCourses;
        this.overallAttendance = overallAttendance;
    }
}
