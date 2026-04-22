package com.example.firstapp.models;

import java.util.List;

public class Faculty {
    public String id;
    public String name;
    public String email;
    public String department;
    public String designation;
    public String specialization;
    public List<String> teachingLoad;

    public Faculty(String id, String name, String email, String department, String designation, String specialization, List<String> teachingLoad) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.designation = designation;
        this.specialization = specialization;
        this.teachingLoad = teachingLoad;
    }
}
