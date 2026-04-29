package com.example.firstapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.api.ApiClient;
import com.example.firstapp.api.ApiResponse;
import com.example.firstapp.api.dto.CourseDto;
import com.example.firstapp.api.dto.DepartmentDto;
import com.example.firstapp.api.dto.FacultyDto;
import com.example.firstapp.api.dto.StudentDto;
import com.example.firstapp.api.service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DepartmentDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_detail);

        String deptId = getIntent().getStringExtra("DEPARTMENT_ID");
        if (deptId == null) {
            ((TextView) findViewById(R.id.deptNameText)).setText("Department ID missing");
            return;
        }

        DataService dataService = ApiClient.getInstance(this).create(DataService.class);

        // Fetch department details
        dataService.getDepartment(deptId).enqueue(new Callback<ApiResponse<DepartmentDto>>() {
            @Override
            public void onResponse(Call<ApiResponse<DepartmentDto>> call,
                                   Response<ApiResponse<DepartmentDto>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    DepartmentDto dept = response.body().getData();
                    if (dept != null) {
                        ((TextView) findViewById(R.id.deptNameText)).setText(dept.getName() + " (" + dept.getCode() + ")");
                        // Use department stats instead of calling students endpoint
                        if (dept.getStats() != null && dept.getStats().getStudentCount() != null) {
                            ((TextView) findViewById(R.id.studentCountText)).setText("Total Students: " + dept.getStats().getStudentCount());
                        }
                        fetchCourses(dataService, dept.getId());
                    }
                } else {
                    String error = "Failed to load department info";
                    if (response.code() == 401) error = "Session expired";
                    Toast.makeText(DepartmentDetailActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<DepartmentDto>> call, Throwable t) {
                Toast.makeText(DepartmentDetailActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });

        // Fetch faculty in this department
        dataService.getDepartmentFaculty(deptId).enqueue(new Callback<ApiResponse<List<FacultyDto>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<FacultyDto>>> call,
                                   Response<ApiResponse<List<FacultyDto>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    List<FacultyDto> facultyList = response.body().getData();
                    updateFacultyListView(facultyList);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<FacultyDto>>> call, Throwable t) {}
        });
    }

    private void fetchCourses(DataService dataService, String deptId) {
        dataService.getCourses(deptId).enqueue(new Callback<ApiResponse<List<CourseDto>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<CourseDto>>> call,
                                   Response<ApiResponse<List<CourseDto>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    List<CourseDto> courseList = response.body().getData();
                    updateCourseListView(courseList);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<CourseDto>>> call, Throwable t) {}
        });
    }

    private void updateFacultyListView(List<FacultyDto> facultyList) {
        List<String> facultyStrings = new ArrayList<>();
        if (facultyList != null) {
            for (FacultyDto f : facultyList) {
                String name = f.getName() != null ? f.getName() : "Faculty";
                String desig = f.getDesignation() != null ? f.getDesignation() : "";
                facultyStrings.add(name + " - " + desig);
            }
        }
        if (facultyStrings.isEmpty()) facultyStrings.add("No faculty found");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(DepartmentDetailActivity.this, android.R.layout.simple_list_item_1, facultyStrings);
        ((ListView) findViewById(R.id.facultyListView)).setAdapter(adapter);
    }

    private void updateCourseListView(List<CourseDto> courseList) {
        List<String> courseStrings = new ArrayList<>();
        if (courseList != null) {
            for (CourseDto c : courseList) {
                courseStrings.add(c.getName() + " (" + c.getCode() + ")");
            }
        }
        if (courseStrings.isEmpty()) courseStrings.add("No courses found");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(DepartmentDetailActivity.this, android.R.layout.simple_list_item_1, courseStrings);
        ((ListView) findViewById(R.id.courseListView)).setAdapter(adapter);
    }
}
