package com.example.firstapp.models;

public class Department {
    public String id;
    public String name;
    public String code;
    public int totalFaculty;
    public int totalStudents;

    public Department(String id, String name, String code, int totalFaculty, int totalStudents) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.totalFaculty = totalFaculty;
        this.totalStudents = totalStudents;
    }
}
