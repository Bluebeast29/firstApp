package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.api.TokenManager;
import com.example.firstapp.data.LoginRepository;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

        TokenManager tokenManager = TokenManager.getInstance(this);

        TextView tvName = findViewById(R.id.tv_name);
        TextView tvEmail = findViewById(R.id.tv_email);
        TextView tvRole = findViewById(R.id.tv_role);

        if (tvName != null) tvName.setText(tokenManager.getUserName());
        if (tvEmail != null) tvEmail.setText(tokenManager.getUserEmail());
        if (tvRole != null) tvRole.setText(tokenManager.getUserRole() != null ? tokenManager.getUserRole().toUpperCase() : "USER");

        findViewById(R.id.btn_logout).setOnClickListener(v -> {
            LoginRepository.getInstance(this).logout();
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.btn_change_pass).setOnClickListener(v ->
                startActivity(new Intent(this, forgot_password.class)));
    }
}
