package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.models.Session;

import java.util.ArrayList;
import java.util.List;

public class FacultyDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_dashboard);

        ListView scheduleListView = findViewById(R.id.scheduleListView);

        // Load today's schedule
        List<Session> allSessions = MockData.getSessions();
        List<String> scheduleStrings = new ArrayList<>();
        for (Session session : allSessions) {
            scheduleStrings.add(session.courseName + "\n" + session.startTime + " - " + session.endTime + "\n" + session.room + "\n[Tap to Mark Attendance]");
        }

        ArrayAdapter<String> scheduleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scheduleStrings);
        scheduleListView.setAdapter(scheduleAdapter);

        scheduleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Navigate to Mark Attendance Activity
                Intent intent = new Intent(FacultyDashboardActivity.this, MarkAttendanceActivity.class);
                startActivity(intent);
            }
        });

        // Navigation Options List
        ListView navigationListView = findViewById(R.id.navigationListView);
        if (navigationListView != null) {
            String[] navItems = {"My Courses", "View Students", "My Profile"};
            ArrayAdapter<String> navAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, navItems);
            navigationListView.setAdapter(navAdapter);

            navigationListView.setOnItemClickListener((parent, view, position, id) -> {
                if (position == 0) startActivity(new Intent(FacultyDashboardActivity.this, CourseListActivity.class));
                else if (position == 1) startActivity(new Intent(FacultyDashboardActivity.this, StudentListActivity.class));
                else if (position == 2) startActivity(new Intent(FacultyDashboardActivity.this, MyProfileActivity.class));
            });
        }
    }
}
