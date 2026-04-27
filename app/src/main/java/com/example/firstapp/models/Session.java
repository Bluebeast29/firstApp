package com.example.firstapp.models;

public class Session {
    public String id;
    public String date;
    public String startTime;
    public String endTime;
    public String courseName;
    public String facultyName;
    public String room;
    public String status;

    public Session(String id, String date, String startTime, String endTime, String courseName, String facultyName, String room, String status) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.courseName = courseName;
        this.facultyName = facultyName;
        this.room = room;
        this.status = status;
    }
}
