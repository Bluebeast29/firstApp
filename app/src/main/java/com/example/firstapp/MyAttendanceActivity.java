package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.api.ApiClient;
import com.example.firstapp.api.ApiResponse;
import com.example.firstapp.api.dto.AttendanceDto;
import com.example.firstapp.api.dto.AuthResponse;
import com.example.firstapp.api.dto.StudentAttendanceSummaryDto;
import com.example.firstapp.api.dto.StudentDto;
import com.example.firstapp.api.service.DataService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAttendanceActivity extends AppCompatActivity {

    private TextView tvOverallPercentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_attendance);

        tvOverallPercentage = findViewById(R.id.tv_overall_percentage);
        RecyclerView rvAttendance = findViewById(R.id.rv_attendance);
        rvAttendance.setLayoutManager(new LinearLayoutManager(this));

        DataService dataService = ApiClient.getInstance(this).create(DataService.class);

        // Fetch current student record via /auth/me
        dataService.getMyStudentRecord().enqueue(new Callback<ApiResponse<AuthResponse.UserData>>() {
            @Override
            public void onResponse(Call<ApiResponse<AuthResponse.UserData>> call,
                                   Response<ApiResponse<AuthResponse.UserData>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    AuthResponse.UserData user = response.body().getData();
                    if (user != null && user.getStudent() != null) {
                        loadAttendance(dataService, user.getStudent().getId(), rvAttendance);
                    } else if (user != null) {
                        handleFallback(dataService, rvAttendance);
                    } else {
                        showEmpty(rvAttendance, "Student profile not found");
                    }
                } else if (response.code() == 400 || (response.body() != null && !response.body().isSuccess())) {
                    handleFallback(dataService, rvAttendance);
                } else {
                    if (response.code() == 401) {
                        Toast.makeText(MyAttendanceActivity.this, "Session expired", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MyAttendanceActivity.this, login.class));
                        finish();
                    }
                    showEmpty(rvAttendance, "Unable to load student record");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<AuthResponse.UserData>> call, Throwable t) {
                showEmpty(rvAttendance, "Network error");
            }
        });
    }

    private void handleFallback(DataService dataService, RecyclerView rvAttendance) {
        // Fallback: If 'me' endpoint fails, try to find the student in the full list using the userId from token
        String currentUserId = com.example.firstapp.api.TokenManager.getInstance(MyAttendanceActivity.this).getUserId();
        if (currentUserId != null) {
            dataService.getStudents().enqueue(new Callback<ApiResponse<List<StudentDto>>>() {
                @Override
                public void onResponse(Call<ApiResponse<List<StudentDto>>> call, Response<ApiResponse<List<StudentDto>>> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                        List<StudentDto> allStudents = response.body().getData();
                        StudentDto found = null;
                        if (allStudents != null) {
                            for (StudentDto s : allStudents) {
                                if (s.getUserId() != null && currentUserId.equals(s.getUserId().id)) {
                                    found = s;
                                    break;
                                }
                            }
                        }
                        if (found != null) {
                            loadAttendance(dataService, found.getId(), rvAttendance);
                        } else {
                            showEmpty(rvAttendance, "Could not find your student record");
                        }
                    } else {
                        showEmpty(rvAttendance, "Unable to load student record");
                    }
                }
                @Override
                public void onFailure(Call<ApiResponse<List<StudentDto>>> call, Throwable t) {
                    showEmpty(rvAttendance, "Network error during fallback");
                }
            });
        } else {
            showEmpty(rvAttendance, "Unable to load student record");
        }
    }

    private void loadAttendance(DataService dataService, String studentId, RecyclerView rvAttendance) {
        dataService.getAttendanceSummaryByStudent(studentId).enqueue(new Callback<ApiResponse<StudentAttendanceSummaryDto>>() {
            @Override
            public void onResponse(Call<ApiResponse<StudentAttendanceSummaryDto>> call,
                                   Response<ApiResponse<StudentAttendanceSummaryDto>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    StudentAttendanceSummaryDto summary = response.body().getData();
                    List<String> displayData = new ArrayList<>();

                    if (summary != null) {
                        // Update overall percentage text
                        if (tvOverallPercentage != null) {
                            tvOverallPercentage.setText(String.format(Locale.getDefault(), "%.0f%%", summary.getPercentage()));
                        }

                        List<StudentAttendanceSummaryDto.CourseAttendanceSummary> courseSummaries = summary.getByCourse();
                        if (courseSummaries != null && !courseSummaries.isEmpty()) {
                            for (StudentAttendanceSummaryDto.CourseAttendanceSummary cs : courseSummaries) {
                                String courseInfo = String.format(Locale.getDefault(), "%s (%s): %.0f%%",
                                        cs.getCourseName(), cs.getCourseCode(), cs.getPercentage());
                                String details = String.format(Locale.getDefault(), "Present: %d / Total: %d",
                                        cs.getPresent(), cs.getTotalSessions());
                                displayData.add(courseInfo + "\n" + details);
                            }
                        }
                    }

                    if (displayData.isEmpty()) {
                        displayData.add("No attendance breakdown found");
                    }

                    GenericAdapter adapter = new GenericAdapter(displayData);
                    rvAttendance.setAdapter(adapter);
                } else {
                    showEmpty(rvAttendance, "Failed to load attendance summary");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<StudentAttendanceSummaryDto>> call, Throwable t) {
                showEmpty(rvAttendance, "Network error fetching attendance");
            }
        });
    }

    private void showEmpty(RecyclerView rv, String message) {
        List<String> empty = new ArrayList<>();
        empty.add(message);
        rv.setAdapter(new GenericAdapter(empty));
    }
}
