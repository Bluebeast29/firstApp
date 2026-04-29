package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.api.ApiClient;
import com.example.firstapp.api.ApiResponse;
import com.example.firstapp.api.TokenManager;
import com.example.firstapp.api.dto.FacultyDto;
import com.example.firstapp.api.dto.OfferingDto;
import com.example.firstapp.api.service.DataService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_teacher);

        setupNavigation();
        setupClickListeners();
        loadFacultyData();
    }

    private void loadFacultyData() {
        TokenManager tokenManager = TokenManager.getInstance(this);
        String userId = tokenManager.getUserId();
        DataService dataService = ApiClient.getInstance(this).create(DataService.class);

        dataService.getFaculty().enqueue(new Callback<ApiResponse<List<FacultyDto>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<FacultyDto>>> call,
                                   Response<ApiResponse<List<FacultyDto>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    List<FacultyDto> facultyList = response.body().getData();
                    if (facultyList != null) {
                        for (FacultyDto f : facultyList) {
                            if (userId != null && userId.equals(f.getUserId())) {
                                loadFacultyOfferings(dataService, f.getId());
                                return;
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<FacultyDto>>> call, Throwable t) {}
        });
    }

    private void loadFacultyOfferings(DataService dataService, String facultyId) {
        dataService.getFacultyOfferings(facultyId).enqueue(new Callback<ApiResponse<List<OfferingDto>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<OfferingDto>>> call,
                                   Response<ApiResponse<List<OfferingDto>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    List<OfferingDto> offerings = response.body().getData();
                    if (offerings != null && !offerings.isEmpty()) {
                        OfferingDto first = offerings.get(0);
                        StringBuilder sb = new StringBuilder();
                        sb.append(first.getCourseName() != null ? first.getCourseName() : "Course");
                        if (first.getSection() != null) sb.append(" (").append(first.getSection()).append(")");
                        final String courseLabel = sb.toString();

                        View card = findViewById(R.id.card_task1);
                        if (card != null) {
                            card.setOnClickListener(v -> {
                                Intent intent = new Intent(TeacherHomeActivity.this, MarkAttendanceActivity.class);
                                intent.putExtra("SUBJECT", courseLabel);
                                intent.putExtra("OFFERING_ID", first.getId());
                                startActivity(intent);
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<OfferingDto>>> call, Throwable t) {}
        });
    }

    private void setupNavigation() {
        findViewById(R.id.nav_modules).setOnClickListener(v ->
                startActivity(new Intent(this, ERPModulesActivity.class)));

        findViewById(R.id.nav_attendance).setOnClickListener(v ->
                startActivity(new Intent(this, MarkAttendanceActivity.class)));

        findViewById(R.id.nav_profile).setOnClickListener(v ->
                startActivity(new Intent(this, UserProfileActivity.class)));
    }

    private void setupClickListeners() {
        View card = findViewById(R.id.card_task1);
        if (card != null) {
            card.setOnClickListener(v -> {
                Intent intent = new Intent(this, MarkAttendanceActivity.class);
                startActivity(intent);
            });
        }
    }
}
