package com.example.firstapp.api;

import com.google.gson.annotations.SerializedName;

public class ApiResponse<T> {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private T data;

    @SerializedName("error")
    private String error;

    @SerializedName("pagination")
    private Pagination pagination;

    @SerializedName("summary")
    private AttendanceSummary summary;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public AttendanceSummary getSummary() {
        return summary;
    }

    public static class Pagination {
        public int page;
        public int limit;
        public int total;
        public int totalPages;
    }

    public static class AttendanceSummary {
        public int total;
        public int present;
        public int absent;
        public int late;
        public int excused;
        public double percentage;
    }
}
