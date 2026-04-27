package com.example.firstapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.models.Session;

import java.util.ArrayList;
import java.util.List;

public class StudentDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        ListView sessionsListView = findViewById(R.id.sessionsListView);
        ListView activityListView = findViewById(R.id.activityListView);

        // Load upcoming sessions
        List<Session> allSessions = MockData.getSessions();
        List<String> sessionStrings = new ArrayList<>();
        for (Session session : allSessions) {
            if ("Upcoming".equals(session.status)) {
                sessionStrings.add(session.courseName + "\n" + session.date + " " + session.startTime + "\n" + session.room);
            }
        }
        ArrayAdapter<String> sessionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sessionStrings);
        sessionsListView.setAdapter(sessionAdapter);

        // Load recent activity
        List<String> activities = new ArrayList<>();
        activities.add("Attendance marked for CS201");
        activities.add("Assignment submitted for Math201");
        activities.add("Grade updated for CS101");

        ArrayAdapter<String> activityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, activities);
        activityListView.setAdapter(activityAdapter);

        // Navigation Options List
        ListView navigationListView = findViewById(R.id.navigationListView);
        if (navigationListView != null) {
            String[] navItems = {"My Courses", "My Attendance", "My Profile"};
            ArrayAdapter<String> navAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, navItems);
            navigationListView.setAdapter(navAdapter);

            navigationListView.setOnItemClickListener((parent, view, position, id) -> {
                if (position == 0) startActivity(new android.content.Intent(StudentDashboardActivity.this, CourseListActivity.class));
                else if (position == 1) startActivity(new android.content.Intent(StudentDashboardActivity.this, MyAttendanceActivity.class));
                else if (position == 2) startActivity(new android.content.Intent(StudentDashboardActivity.this, MyProfileActivity.class));
            });
        }
    }
}
