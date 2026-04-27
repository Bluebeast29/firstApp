package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class ERPModulesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erp_modules);

        setupClickListeners();
    }

    private void setupClickListeners() {
        findViewById(R.id.btn_departments).setOnClickListener(v -> openList("Departments"));
        findViewById(R.id.btn_courses).setOnClickListener(v -> openList("Courses"));
        findViewById(R.id.btn_students).setOnClickListener(v -> openList("Students"));
        findViewById(R.id.btn_faculty).setOnClickListener(v -> openList("Faculty"));
        findViewById(R.id.btn_schedule).setOnClickListener(v -> openList("Schedule"));
        
        findViewById(R.id.btn_mark_attendance).setOnClickListener(v -> {
            Intent intent = new Intent(this, MarkAttendanceActivity.class);
            intent.putExtra("SUBJECT", "Manual Entry");
            startActivity(intent);
        });
        
        findViewById(R.id.btn_my_attendance).setOnClickListener(v -> {
            startActivity(new Intent(this, MyAttendanceActivity.class));
        });
    }

    private void openList(String type) {
        Intent intent = new Intent(this, GenericListActivity.class);
        intent.putExtra("LIST_TYPE", type);
        startActivity(intent);
    }
}