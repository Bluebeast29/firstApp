package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

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
        
        String[][] courses = {
            {"Data Analysis & Algorithms", "92"},
            {"Discrete Mathematics", "88"},
            {"Database Management", "85"},
            {"Computer Networks", "78"}
        };

        LayoutInflater inflater = LayoutInflater.from(this);
        for (String[] course : courses) {
            View view = inflater.inflate(R.layout.item_course_glass, container, false);
            ((TextView)view.findViewById(R.id.tv_course_name)).setText(course[0]);
            int progress = Integer.parseInt(course[1]);
            ((ProgressBar)view.findViewById(R.id.pb_course)).setProgress(progress);
            ((TextView)view.findViewById(R.id.tv_course_percent)).setText("Attendance: " + progress + "%");
            container.addView(view);
        }
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