package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.api.ApiClient;
import com.example.firstapp.api.ApiResponse;
import com.example.firstapp.api.TokenManager;
import com.example.firstapp.api.dto.AttendanceDto;
import com.example.firstapp.api.dto.AuthResponse;
import com.example.firstapp.api.dto.EnrollmentDto;
import com.example.firstapp.api.dto.StudentDto;
import com.example.firstapp.api.dto.StudentAttendanceSummaryDto;
import com.example.firstapp.api.service.DataService;
import com.example.firstapp.data.LoginRepository;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupCourses();
        setupNavigation();
    }

    private void setupCourses() {
        LinearLayout container = findViewById(R.id.courses_container);
        if (container == null) return;
        container.removeAllViews();

        DataService dataService = ApiClient.getInstance(this).create(DataService.class);

        // Fetch current student's record directly via /auth/me
        dataService.getMyStudentRecord().enqueue(new Callback<ApiResponse<AuthResponse.UserData>>() {
            @Override
            public void onResponse(Call<ApiResponse<AuthResponse.UserData>> call,
                                   Response<ApiResponse<AuthResponse.UserData>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    AuthResponse.UserData user = response.body().getData();
                    if (user != null) {
                        updateUserName(user.getName());
                    }
                    if (user != null && user.getStudent() != null) {
                        fetchStudentData(dataService, user.getStudent().getId(), container);
                        fetchAttendance(dataService, user.getStudent().getId());
                    } else if (user != null) {
                         // User logged in but maybe student profile not linked in /auth/me
                         handleFallback(dataService, container);
                    } else {
                        showEmptyState(container, "Student record not found");
                    }
                } else if (response.code() == 400 || (response.body() != null && !response.body().isSuccess())) {
                    handleFallback(dataService, container);
                } else {
                    if (response.code() == 401) {
                        handleUnauthorized();
                    } else {
                        showEmptyState(container, "Unable to load courses");
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<AuthResponse.UserData>> call, Throwable t) {
                showEmptyState(container, "Network error: " + t.getMessage());
            }
        });
    }

    private void handleFallback(DataService dataService, LinearLayout container) {
        // Fallback: lookup by User ID if 'me' endpoint doesn't return student data directly
        String currentUserId = com.example.firstapp.api.TokenManager.getInstance(home.this).getUserId();
        if (currentUserId != null) {
            dataService.getStudents().enqueue(new Callback<ApiResponse<List<StudentDto>>>() {
                @Override
                public void onResponse(Call<ApiResponse<List<StudentDto>>> call, Response<ApiResponse<List<StudentDto>>> resp) {
                    if (resp.isSuccessful() && resp.body() != null && resp.body().isSuccess()) {
                        List<StudentDto> all = resp.body().getData();
                        StudentDto found = null;
                        if (all != null) {
                            for (StudentDto s : all) {
                                if (s.getUserId() != null && currentUserId.equals(s.getUserId().id)) {
                                    found = s;
                                    break;
                                }
                            }
                        }
                        if (found != null) {
                            fetchStudentData(dataService, found.getId(), container);
                            fetchAttendance(dataService, found.getId());
                            if (found.getName() != null) {
                                updateUserName(found.getName());
                            }
                        } else {
                            showEmptyState(container, "Profile not found");
                        }
                    } else {
                        showEmptyState(container, "Unable to load courses");
                    }
                }
                @Override
                public void onFailure(Call<ApiResponse<List<StudentDto>>> call, Throwable t) {
                    showEmptyState(container, "Network error during fallback");
                }
            });
        } else {
            showEmptyState(container, "Unable to load courses");
        }
    }

    private void handleUnauthorized() {
        Toast.makeText(this, "Session expired. Please login again.", Toast.LENGTH_LONG).show();
        LoginRepository.getInstance(this).logout();
        Intent intent = new Intent(this, login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void updateUserName(String name) {
        TextView tvName = findViewById(R.id.tv_user_name);
        if (tvName != null && name != null) {
            tvName.setText(name);
        }
    }

    private void fetchAttendance(DataService dataService, String studentId) {
        dataService.getAttendanceSummaryByStudent(studentId).enqueue(new Callback<ApiResponse<StudentAttendanceSummaryDto>>() {
            @Override
            public void onResponse(Call<ApiResponse<StudentAttendanceSummaryDto>> call, Response<ApiResponse<StudentAttendanceSummaryDto>> response) {
                TextView tvAttendance = findViewById(R.id.tv_attendance_percent);
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    StudentAttendanceSummaryDto summary = response.body().getData();
                    if (summary != null && summary.getTotalSessions() > 0) {
                        if (tvAttendance != null) {
                            tvAttendance.setText(String.format(Locale.getDefault(), "%.0f%%", summary.getPercentage()));
                        }
                    } else if (tvAttendance != null) {
                        tvAttendance.setText("N/A");
                    }
                } else if (tvAttendance != null) {
                    tvAttendance.setText("N/A");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<StudentAttendanceSummaryDto>> call, Throwable t) {
                TextView tvAttendance = findViewById(R.id.tv_attendance_percent);
                if (tvAttendance != null) {
                    tvAttendance.setText("N/A");
                }
            }
        });
    }

    private void fetchStudentData(DataService dataService, String studentId, LinearLayout container) {
        dataService.getStudentEnrollments(studentId).enqueue(new Callback<ApiResponse<List<EnrollmentDto>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<EnrollmentDto>>> call,
                                   Response<ApiResponse<List<EnrollmentDto>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    List<EnrollmentDto> enrollments = response.body().getData();
                    renderCourses(container, enrollments);
                } else {
                    if (response.code() == 401) {
                        handleUnauthorized();
                    } else {
                        showEmptyState(container, "No enrollments found");
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<EnrollmentDto>>> call, Throwable t) {
                showEmptyState(container, "Error fetching enrollments");
            }
        });
    }

    private void renderCourses(LinearLayout container, List<EnrollmentDto> enrollments) {
        container.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);

        if (enrollments == null || enrollments.isEmpty()) {
            showEmptyState(container, "No courses enrolled yet");
            return;
        }

        for (EnrollmentDto enrollment : enrollments) {
            String name = enrollment.getCourseName() != null ? enrollment.getCourseName() : "Course";
            View view = inflater.inflate(R.layout.item_course_glass, container, false);
            ((TextView) view.findViewById(R.id.tv_course_name)).setText(name);
            ((ProgressBar) view.findViewById(R.id.pb_course)).setProgress(0);
            ((TextView) view.findViewById(R.id.tv_course_percent)).setText(enrollment.getStatus());
            container.addView(view);
        }
    }

    private void showEmptyState(LinearLayout container, String message) {
        container.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.item_course_glass, container, false);
        ((TextView) view.findViewById(R.id.tv_course_name)).setText(message);
        ((ProgressBar) view.findViewById(R.id.pb_course)).setProgress(0);
        ((TextView) view.findViewById(R.id.tv_course_percent)).setText("");
        container.addView(view);
    }

    private void setupNavigation() {
        findViewById(R.id.nav_modules).setOnClickListener(v ->
                startActivity(new Intent(this, ERPModulesActivity.class)));

        findViewById(R.id.nav_attendance).setOnClickListener(v ->
                startActivity(new Intent(this, MyAttendanceActivity.class)));

        findViewById(R.id.nav_profile).setOnClickListener(v ->
                startActivity(new Intent(this, UserProfileActivity.class)));
    }
}
