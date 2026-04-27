package com.example.firstapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AdminDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        ListView pendingApprovalsListView = findViewById(R.id.pendingApprovalsListView);

        // Load pending approvals
        List<String> approvals = new ArrayList<>();
        approvals.add("Eve Carter - Student");
        approvals.add("Dr. Grace Hopper - Faculty");
        approvals.add("Tom Hardy - Staff");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, approvals);
        pendingApprovalsListView.setAdapter(adapter);

        // Navigation Options List
        ListView navigationListView = findViewById(R.id.navigationListView);
        if (navigationListView != null) {
            String[] navItems = {"Manage Departments", "Manage Courses", "Manage Students", "Manage Faculty", "My Profile"};
            ArrayAdapter<String> navAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, navItems);
            navigationListView.setAdapter(navAdapter);

            navigationListView.setOnItemClickListener((parent, view, position, id) -> {
                if (position == 0) startActivity(new android.content.Intent(AdminDashboardActivity.this, DepartmentListActivity.class));
                else if (position == 1) startActivity(new android.content.Intent(AdminDashboardActivity.this, CourseListActivity.class));
                else if (position == 2) startActivity(new android.content.Intent(AdminDashboardActivity.this, StudentListActivity.class));
                else if (position == 3) startActivity(new android.content.Intent(AdminDashboardActivity.this, FacultyListActivity.class));
                else if (position == 4) startActivity(new android.content.Intent(AdminDashboardActivity.this, MyProfileActivity.class));
            });
        }
    }
}
