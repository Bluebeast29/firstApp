package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.api.ApiClient;
import com.example.firstapp.api.ApiResponse;
import com.example.firstapp.api.dto.DepartmentDto;
import com.example.firstapp.api.dto.FacultyDto;
import com.example.firstapp.api.dto.StudentDto;
import com.example.firstapp.api.service.DataService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        setupNavigation();
        setupClickListeners();
        loadDashboardStats();
    }

    private void loadDashboardStats() {
        DataService dataService = ApiClient.getInstance(this).create(DataService.class);

        dataService.getStudents().enqueue(new Callback<ApiResponse<List<StudentDto>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<StudentDto>>> call,
                                   Response<ApiResponse<List<StudentDto>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    List<StudentDto> students = response.body().getData();
                    int id = getResources().getIdentifier("tv_student_count", "id", getPackageName());
                    if (id != 0) { TextView tv = findViewById(id); if (tv != null && students != null) tv.setText(students.size() + " Students"); }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<StudentDto>>> call, Throwable t) {}
        });

        dataService.getFaculty().enqueue(new Callback<ApiResponse<List<FacultyDto>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<FacultyDto>>> call,
                                   Response<ApiResponse<List<FacultyDto>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    List<FacultyDto> faculty = response.body().getData();
                    int id = getResources().getIdentifier("tv_faculty_count", "id", getPackageName());
                    if (id != 0) { TextView tv = findViewById(id); if (tv != null && faculty != null) tv.setText(faculty.size() + " Faculty"); }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<FacultyDto>>> call, Throwable t) {}
        });

        dataService.getDepartments().enqueue(new Callback<ApiResponse<List<DepartmentDto>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<DepartmentDto>>> call,
                                   Response<ApiResponse<List<DepartmentDto>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    List<DepartmentDto> depts = response.body().getData();
                    int id = getResources().getIdentifier("tv_dept_count", "id", getPackageName());
                    if (id != 0) { TextView tv = findViewById(id); if (tv != null && depts != null) tv.setText(depts.size() + " Departments"); }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<DepartmentDto>>> call, Throwable t) {}
        });
    }

    private void setupNavigation() {
        findViewById(R.id.nav_modules).setOnClickListener(v ->
                startActivity(new Intent(this, ERPModulesActivity.class)));

        findViewById(R.id.nav_profile).setOnClickListener(v ->
                startActivity(new Intent(this, UserProfileActivity.class)));
    }

    private void setupClickListeners() {
        findViewById(R.id.btn_manage_users).setOnClickListener(v -> {
            startActivity(new Intent(this, StudentListActivity.class));
        });
    }
}
