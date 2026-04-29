package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.api.TokenManager;
import com.example.firstapp.data.LoginRepository;

public class MyProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        TokenManager tokenManager = TokenManager.getInstance(this);

        // Display user info
        int idName = getResources().getIdentifier("tv_profile_name", "id", getPackageName());
        int idEmail = getResources().getIdentifier("tv_profile_email", "id", getPackageName());
        int idRole = getResources().getIdentifier("tv_profile_role", "id", getPackageName());
        if (idName != 0) { TextView tvName = findViewById(idName); if (tvName != null) tvName.setText(tokenManager.getUserName()); }
        if (idEmail != 0) { TextView tvEmail = findViewById(idEmail); if (tvEmail != null) tvEmail.setText(tokenManager.getUserEmail()); }
        if (idRole != 0) { TextView tvRole = findViewById(idRole); if (tvRole != null) tvRole.setText(tokenManager.getUserRole()); }

        Button changePasswordButton = findViewById(R.id.changePasswordButton);
        Button logoutButton = findViewById(R.id.logoutButton);

        changePasswordButton.setOnClickListener(v ->
                startActivity(new Intent(this, forgot_password.class)));

        logoutButton.setOnClickListener(v -> {
            LoginRepository.getInstance(this).logout();
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}
