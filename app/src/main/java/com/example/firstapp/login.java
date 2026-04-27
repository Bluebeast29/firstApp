package com.example.firstapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class login extends AppCompatActivity {

    private TextInputEditText etEnroll, etPass;
    private TextInputLayout tilEnroll, tilPass;
    private ProgressBar progressBar;
    private View btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        etEnroll = (TextInputEditText) ((TextInputLayout)findViewById(R.id.input_enroll)).getEditText();
        etPass = (TextInputEditText) ((TextInputLayout)findViewById(R.id.input_pass)).getEditText();
        tilEnroll = findViewById(R.id.input_enroll);
        tilPass = findViewById(R.id.input_pass);
        progressBar = findViewById(R.id.login_progress);
        btnLogin = findViewById(R.id.login);
    }

    private void setupClickListeners() {
        if (btnLogin != null) {
            btnLogin.setOnClickListener(v -> attemptLogin());
        }
        
        View signupLink = findViewById(R.id.signupText);
        if (signupLink != null) {
            signupLink.setOnClickListener(v -> 
                startActivity(new Intent(this, signup.class)));
        }
            
        View forgotLink = findViewById(R.id.forgot_password);
        if (forgotLink != null) {
            forgotLink.setOnClickListener(v -> 
                startActivity(new Intent(this, forgot_password.class)));
        }

        // Dev Shortcuts
        setupDevShortcuts();

        // Hide keyboard on background touch
        View main = findViewById(R.id.main);
        if (main != null) main.setOnClickListener(v -> hideKeyboard());
        View mainCard = findViewById(R.id.mainCard);
        if (mainCard != null) mainCard.setOnClickListener(v -> hideKeyboard());
    }

    private void setupDevShortcuts() {
        View s = findViewById(R.id.btn_dev_student);
        if (s != null) s.setOnClickListener(v -> navigateToDashboard("Student"));
        View t = findViewById(R.id.btn_dev_teacher);
        if (t != null) t.setOnClickListener(v -> navigateToDashboard("Teacher"));
        View a = findViewById(R.id.btn_dev_admin);
        if (a != null) a.setOnClickListener(v -> navigateToDashboard("Admin"));
    }

    private void attemptLogin() {
        hideKeyboard();
        if (etEnroll == null || etPass == null) return;
        
        String enroll = etEnroll.getText().toString().trim();
        String pass = etPass.getText().toString().trim();

        if (!validateInputs(enroll, pass)) return;

        setLoading(true);

        // Mock login delay
        new Handler().postDelayed(() -> {
            setLoading(false);
            navigateToDashboard("Student");
        }, 1500);
    }

    private boolean validateInputs(String enroll, String pass) {
        boolean valid = true;
        if (TextUtils.isEmpty(enroll)) {
            tilEnroll.setError("Enrollment number is required");
            valid = false;
        } else tilEnroll.setError(null);

        if (TextUtils.isEmpty(pass)) {
            tilPass.setError("Password is required");
            valid = false;
        } else if (pass.length() < 6) {
            tilPass.setError("Minimum 6 characters");
            valid = false;
        } else tilPass.setError(null);

        return valid;
    }

    private void setLoading(boolean isLoading) {
        if (progressBar != null) progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        if (btnLogin != null) {
            btnLogin.setEnabled(!isLoading);
            btnLogin.setAlpha(isLoading ? 0.5f : 1.0f);
        }
    }

    private void navigateToDashboard(String role) {
        Intent intent;
        switch (role) {
            case "Teacher": intent = new Intent(this, TeacherHomeActivity.class); break;
            case "Admin": intent = new Intent(this, AdminHomeActivity.class); break;
            default: intent = new Intent(this, home.class); break;
        }
        startActivity(intent);
        finish();
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}