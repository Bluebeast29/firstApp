package com.example.firstapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.data.LoginDataSource;
import com.example.firstapp.data.LoginRepository;
import com.example.firstapp.data.model.LoggedInUser;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class login extends AppCompatActivity {

    private TextInputEditText etEmail, etPass;
    private TextInputLayout tilEmail, tilPass;
    private ProgressBar progressBar;
    private View btnLogin;
    private LoginRepository loginRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginRepository = LoginRepository.getInstance(this);

        // Auto-navigate if already logged in
        if (loginRepository.isLoggedIn()) {
            navigateToDashboard(loginRepository.getUser().getRole());
            return;
        }

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        etEmail = (TextInputEditText) ((TextInputLayout) findViewById(R.id.input_email)).getEditText();
        etPass = (TextInputEditText) ((TextInputLayout) findViewById(R.id.input_pass)).getEditText();
        tilEmail = findViewById(R.id.input_email);
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

        View main = findViewById(R.id.main);
        if (main != null) main.setOnClickListener(v -> hideKeyboard());
        View mainCard = findViewById(R.id.mainCard);
        if (mainCard != null) mainCard.setOnClickListener(v -> hideKeyboard());
    }

    private void attemptLogin() {
        hideKeyboard();
        if (etEmail == null || etPass == null) return;

        String email = etEmail.getText().toString().trim();
        String pass = etPass.getText().toString().trim();

        if (!validateInputs(email, pass)) return;

        setLoading(true);

        loginRepository.login(email, pass, new LoginDataSource.AuthCallback() {
            @Override
            public void onSuccess(LoggedInUser user) {
                runOnUiThread(() -> {
                    setLoading(false);
                    navigateToDashboard(user.getRole());
                });
            }

            @Override
            public void onError(String message) {
                runOnUiThread(() -> {
                    setLoading(false);
                    Toast.makeText(login.this, message, Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private boolean validateInputs(String email, String pass) {
        boolean valid = true;
        if (TextUtils.isEmpty(email)) {
            tilEmail.setError("Email is required");
            valid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Enter a valid email address");
            valid = false;
        } else tilEmail.setError(null);

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
        if (role == null) role = "student";

        switch (role) {
            case "faculty":
            case "dept_head":
                intent = new Intent(this, TeacherHomeActivity.class);
                break;
            case "super_admin":
            case "college_admin":
            case "admin":
                intent = new Intent(this, AdminHomeActivity.class);
                break;
            default: // student, staff
                intent = new Intent(this, home.class);
                break;
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
