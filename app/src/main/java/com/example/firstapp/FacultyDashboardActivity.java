package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.api.ApiClient;
import com.example.firstapp.api.ApiResponse;
import com.example.firstapp.api.dto.SessionDto;
import com.example.firstapp.api.service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FacultyDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_dashboard);

        ListView scheduleListView = findViewById(R.id.scheduleListView);

        DataService dataService = ApiClient.getInstance(this).create(DataService.class);

        // Load sessions from API
        dataService.getSessions().enqueue(new Callback<ApiResponse<List<SessionDto>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<SessionDto>>> call,
                                   Response<ApiResponse<List<SessionDto>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    List<SessionDto> sessions = response.body().getData();
                    List<String> scheduleStrings = new ArrayList<>();
                    if (sessions != null) {
                        for (SessionDto session : sessions) {
                            String courseName = session.getCourseName() != null ? session.getCourseName() : "Session";
                            String location = session.getLocation() != null ? session.getLocation() : "";
                            scheduleStrings.add(courseName + "\n" + session.getStartTime() + " - " + session.getEndTime() + "\n" + location + "\n[Tap to Mark Attendance]");
                        }
                    }
                    if (scheduleStrings.isEmpty()) {
                        scheduleStrings.add("No sessions scheduled");
                    }
                    ArrayAdapter<String> scheduleAdapter = new ArrayAdapter<>(FacultyDashboardActivity.this, android.R.layout.simple_list_item_1, scheduleStrings);
                    scheduleListView.setAdapter(scheduleAdapter);
                } else {
                    showFallback(scheduleListView);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<SessionDto>>> call, Throwable t) {
                showFallback(scheduleListView);
            }
        });

        scheduleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(FacultyDashboardActivity.this, MarkAttendanceActivity.class));
            }
        });

        // Navigation
        ListView navigationListView = findViewById(R.id.navigationListView);
        if (navigationListView != null) {
            String[] navItems = {"My Courses", "View Students", "My Profile"};
            ArrayAdapter<String> navAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, navItems);
            navigationListView.setAdapter(navAdapter);

            navigationListView.setOnItemClickListener((parent, view, position, id) -> {
                if (position == 0) startActivity(new Intent(this, CourseListActivity.class));
                else if (position == 1) startActivity(new Intent(this, StudentListActivity.class));
                else if (position == 2) startActivity(new Intent(this, MyProfileActivity.class));
            });
        }
    }

    private void showFallback(ListView listView) {
        List<String> fallback = new ArrayList<>();
        fallback.add("Unable to load schedule");
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fallback));
    }
}
