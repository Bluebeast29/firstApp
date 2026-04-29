package com.example.firstapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.api.ApiClient;
import com.example.firstapp.api.ApiResponse;
import com.example.firstapp.api.dto.StudentDto;
import com.example.firstapp.api.service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        ListView pendingApprovalsListView = findViewById(R.id.pendingApprovalsListView);

        DataService dataService = ApiClient.getInstance(this).create(DataService.class);

        // Load recent students as a proxy for pending approvals
        dataService.getStudents().enqueue(new Callback<ApiResponse<List<StudentDto>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<StudentDto>>> call,
                                   Response<ApiResponse<List<StudentDto>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    List<StudentDto> students = response.body().getData();
                    List<String> approvals = new ArrayList<>();
                    if (students != null) {
                        // Show latest students as "recent activity"
                        int count = Math.min(students.size(), 5);
                        for (int i = 0; i < count; i++) {
                            StudentDto s = students.get(i);
                            String name = s.getName() != null ? s.getName() : "Student";
                            approvals.add(name + " - " + s.getRollNumber());
                        }
                    }
                    if (approvals.isEmpty()) {
                        approvals.add("No recent registrations");
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AdminDashboardActivity.this, android.R.layout.simple_list_item_1, approvals);
                    pendingApprovalsListView.setAdapter(adapter);
                } else {
                    showFallback(pendingApprovalsListView);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<StudentDto>>> call, Throwable t) {
                showFallback(pendingApprovalsListView);
            }
        });

        // Navigation
        ListView navigationListView = findViewById(R.id.navigationListView);
        if (navigationListView != null) {
            String[] navItems = {"Manage Departments", "Manage Courses", "Manage Students", "Manage Faculty", "My Profile"};
            ArrayAdapter<String> navAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, navItems);
            navigationListView.setAdapter(navAdapter);

            navigationListView.setOnItemClickListener((parent, view, position, id) -> {
                if (position == 0) startActivity(new android.content.Intent(this, DepartmentListActivity.class));
                else if (position == 1) startActivity(new android.content.Intent(this, CourseListActivity.class));
                else if (position == 2) startActivity(new android.content.Intent(this, StudentListActivity.class));
                else if (position == 3) startActivity(new android.content.Intent(this, FacultyListActivity.class));
                else if (position == 4) startActivity(new android.content.Intent(this, MyProfileActivity.class));
            });
        }
    }

    private void showFallback(ListView listView) {
        List<String> fallback = new ArrayList<>();
        fallback.add("Unable to load data");
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fallback));
    }
}
