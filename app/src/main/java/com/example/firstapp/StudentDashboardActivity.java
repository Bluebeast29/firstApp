package com.example.firstapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

public class StudentDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        ListView sessionsListView = findViewById(R.id.sessionsListView);
        ListView activityListView = findViewById(R.id.activityListView);

        DataService dataService = ApiClient.getInstance(this).create(DataService.class);

        // Load upcoming sessions from API
        dataService.getSessions().enqueue(new Callback<ApiResponse<List<SessionDto>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<SessionDto>>> call,
                                   Response<ApiResponse<List<SessionDto>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    List<SessionDto> sessions = response.body().getData();
                    List<String> sessionStrings = new ArrayList<>();
                    if (sessions != null) {
                        for (SessionDto session : sessions) {
                            if ("scheduled".equalsIgnoreCase(session.getStatus())) {
                                String courseName = session.getCourseName() != null ? session.getCourseName() : "Session";
                                String location = session.getLocation() != null ? session.getLocation() : "";
                                sessionStrings.add(courseName + "\n" + session.getDate() + " " + session.getStartTime() + "\n" + location);
                            }
                        }
                    }
                    if (sessionStrings.isEmpty()) {
                        sessionStrings.add("No upcoming sessions");
                    }
                    ArrayAdapter<String> sessionAdapter = new ArrayAdapter<>(StudentDashboardActivity.this, android.R.layout.simple_list_item_1, sessionStrings);
                    sessionsListView.setAdapter(sessionAdapter);
                } else {
                    showFallbackSessions(sessionsListView);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<SessionDto>>> call, Throwable t) {
                showFallbackSessions(sessionsListView);
            }
        });

        // Load recent activity (placeholder - backend doesn't have activity feed)
        List<String> activities = new ArrayList<>();
        activities.add("Attendance marked for CS201");
        activities.add("Assignment submitted for Math201");
        activities.add("Grade updated for CS101");
        ArrayAdapter<String> activityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, activities);
        activityListView.setAdapter(activityAdapter);

        // Navigation
        ListView navigationListView = findViewById(R.id.navigationListView);
        if (navigationListView != null) {
            String[] navItems = {"My Courses", "My Attendance", "My Profile"};
            ArrayAdapter<String> navAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, navItems);
            navigationListView.setAdapter(navAdapter);

            navigationListView.setOnItemClickListener((parent, view, position, id) -> {
                if (position == 0)
                    startActivity(new android.content.Intent(this, CourseListActivity.class));
                else if (position == 1)
                    startActivity(new android.content.Intent(this, MyAttendanceActivity.class));
                else if (position == 2)
                    startActivity(new android.content.Intent(this, MyProfileActivity.class));
            });
        }
    }

    private void showFallbackSessions(ListView listView) {
        List<String> fallback = new ArrayList<>();
        fallback.add("Unable to load sessions");
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fallback));
    }
}
