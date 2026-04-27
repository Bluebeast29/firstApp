package com.example.firstapp;

public class Student {
    private String name;
    private String enroll;
    private boolean isPresent;

    public Student(String name, String enroll) {
        this.name = name;
        this.enroll = enroll;
        this.isPresent = false;
    }

    public String getName() { return name; }
    public String getEnroll() { return enroll; }
    public boolean isPresent() { return isPresent; }
    public void setPresent(boolean present) { isPresent = present; }
}