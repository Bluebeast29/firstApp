package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class AdminHomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        
        setupNavigation();
        setupClickListeners();
    }

    private void setupNavigation() {
        findViewById(R.id.nav_modules).setOnClickListener(v -> 
            startActivity(new Intent(this, ERPModulesActivity.class)));
            
        findViewById(R.id.nav_profile).setOnClickListener(v -> 
            startActivity(new Intent(this, UserProfileActivity.class)));
    }

    private void setupClickListeners() {
        findViewById(R.id.btn_manage_users).setOnClickListener(v -> {
            Intent intent = new Intent(this, GenericListActivity.class);
            intent.putExtra("LIST_TYPE", "Students");
            startActivity(intent);
        });
    }
}