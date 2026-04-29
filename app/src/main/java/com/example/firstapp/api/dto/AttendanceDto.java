package com.example.firstapp.api.dto;

import com.google.gson.annotations.SerializedName;

public class AttendanceDto {

    @SerializedName("_id")
    private String id;

    @SerializedName("sessionId")
    private Object sessionId;

    @SerializedName("studentId")
    private String studentId;

    @SerializedName("status")
    private String status;

    @SerializedName("markedAt")
    private String markedAt;

    public String getId() { return id; }
    public Object getSessionId() { return sessionId; }
    public String getStudentId() { return studentId; }
    public String getStatus() { return status; }
    public String getMarkedAt() { return markedAt; }
}
