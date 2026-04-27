package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class TeacherHomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_teacher);
        
        setupNavigation();
        setupClickListeners();
    }

    private void setupNavigation() {
        findViewById(R.id.nav_modules).setOnClickListener(v -> 
            startActivity(new Intent(this, ERPModulesActivity.class)));
            
        findViewById(R.id.nav_attendance).setOnClickListener(v -> 
            startActivity(new Intent(this, MarkAttendanceActivity.class)));
            
        findViewById(R.id.nav_profile).setOnClickListener(v -> 
            startActivity(new Intent(this, UserProfileActivity.class)));
    }

    private void setupClickListeners() {
        findViewById(R.id.card_task1).setOnClickListener(v -> {
            Intent intent = new Intent(this, MarkAttendanceActivity.class);
            intent.putExtra("SUBJECT", "Data Structures (Sec-A)");
            startActivity(intent);
        });
    }
}