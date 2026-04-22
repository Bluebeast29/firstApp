package com.example.firstapp.models;

import java.util.List;

public class Course {
    public String id;
    public String name;
    public String code;
    public String department;
    public int credits;
    public String level;
    public boolean isElective;
    public String description;
    public List<String> prerequisites;
    public List<String> currentOfferings;

    public Course(String id, String name, String code, String department, int credits, String level, boolean isElective, String description, List<String> prerequisites, List<String> currentOfferings) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.department = department;
        this.credits = credits;
        this.level = level;
        this.isElective = isElective;
        this.description = description;
        this.prerequisites = prerequisites;
        this.currentOfferings = currentOfferings;
    }
}
