package com.example.firstapp.api.dto;

import com.google.gson.annotations.SerializedName;

public class EnrollmentDto {

    @SerializedName("_id")
    private String id;

    @SerializedName("studentId")
    private String studentId;

    @SerializedName("offeringId")
    private com.google.gson.JsonElement offeringId;

    @SerializedName("offering")
    private OfferingDto offering;

    @SerializedName("status")
    private String status;

    public String getId() { return id; }
    public String getStudentId() { return studentId; }

    public String getOfferingId() {
        if (offeringId == null || offeringId.isJsonNull()) return null;
        if (offeringId.isJsonPrimitive()) return offeringId.getAsString();
        if (offeringId.isJsonObject()) {
            com.google.gson.JsonElement idElem = offeringId.getAsJsonObject().get("_id");
            if (idElem != null) return idElem.getAsString();
        }
        return null;
    }

    public OfferingDto getOffering() {
        if (offering != null) return offering;
        if (offeringId != null && offeringId.isJsonObject()) {
            return new com.google.gson.Gson().fromJson(offeringId, OfferingDto.class);
        }
        return null;
    }

    public String getStatus() { return status; }

    public String getCourseName() {
        OfferingDto off = getOffering();
        return (off != null && off.getCourseId() != null) ? off.getCourseId().getName() : null;
    }

    public String getCourseCode() {
        OfferingDto off = getOffering();
        return (off != null && off.getCourseId() != null) ? off.getCourseId().getCode() : null;
    }
}
