package com.example.firstapp.api.dto;

import com.google.gson.annotations.SerializedName;

public class SessionDto {

    @SerializedName("_id")
    private String id;

    @SerializedName("offeringId")
    private OfferingDto offeringId;

    @SerializedName("date")
    private String date;

    @SerializedName("startTime")
    private String startTime;

    @SerializedName("endTime")
    private String endTime;

    @SerializedName("location")
    private String location;

    @SerializedName("status")
    private String status;

    public String getId() { return id; }
    public OfferingDto getOfferingId() { return offeringId; }
    public String getDate() { return date; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public String getLocation() { return location; }
    public String getStatus() { return status; }

    public String getCourseName() {
        return (offeringId != null && offeringId.getCourseId() != null) ? offeringId.getCourseId().getName() : null;
    }

    public String getFacultyName() {
        return (offeringId != null && offeringId.getFacultyId() != null) ? offeringId.getFacultyId().getName() : null;
    }
}
