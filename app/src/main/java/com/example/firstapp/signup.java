package com.example.firstapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.api.ApiClient;
import com.example.firstapp.api.ApiResponse;
import com.example.firstapp.api.dto.AuthResponse;
import com.example.firstapp.api.dto.RegisterRequest;
import com.example.firstapp.api.service.AuthService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signup extends AppCompatActivity {

    private TextInputEditText etName, etEmail, etPass, etConfPass;
    private TextInputLayout tilName, tilEmail, tilPass, tilConfPass;
    private AutoCompleteTextView roleDropdown, deptDropdown;
    private ProgressBar progressBar;
    private View btnSignup;
    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        authService = ApiClient.getInstance(this).create(AuthService.class);

        initViews();
        setupDropdowns();
        setupClickListeners();
    }

    private void initViews() {
        tilName = findViewById(R.id.input_name);
        tilEmail = findViewById(R.id.email);
        tilPass = findViewById(R.id.pass);
        tilConfPass = findViewById(R.id.confPass);

        etName = (TextInputEditText) tilName.getEditText();
        etEmail = (TextInputEditText) tilEmail.getEditText();
        etPass = (TextInputEditText) tilPass.getEditText();
        etConfPass = (TextInputEditText) tilConfPass.getEditText();

        roleDropdown = findViewById(R.id.role_dropdown);
        deptDropdown = findViewById(R.id.dept_dropdown);

        btnSignup = findViewById(R.id.signUpButton);
        progressBar = new ProgressBar(this);
    }

    private void setupDropdowns() {
        String[] roles = {"Student", "Teacher", "Admin"};
        ArrayAdapter<String> roleAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, roles);
        roleDropdown.setAdapter(roleAdapter);

        String[] departments = {"Computer Science", "Information Technology", "Mechanical", "Civil", "Electronics"};
        ArrayAdapter<String> deptAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, departments);
        deptDropdown.setAdapter(deptAdapter);
    }

    private void setupClickListeners() {
        btnSignup.setOnClickListener(v -> attemptSignup());
        findViewById(R.id.loginText).setOnClickListener(v -> finish());
        findViewById(R.id.main).setOnClickListener(v -> hideKeyboard());
    }

    private void attemptSignup() {
        hideKeyboard();
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String pass = etPass.getText().toString().trim();
        String confPass = etConfPass.getText().toString().trim();

        if (!validateInputs(name, email, pass, confPass)) return;

        btnSignup.setEnabled(false);
        btnSignup.setAlpha(0.5f);

        String role = mapRole(roleDropdown.getText().toString());
        RegisterRequest request = new RegisterRequest(name, email, pass, role, null);

        authService.register(request).enqueue(new Callback<ApiResponse<AuthResponse.UserData>>() {
            @Override
            public void onResponse(Call<ApiResponse<AuthResponse.UserData>> call,
                                   Response<ApiResponse<AuthResponse.UserData>> response) {
                btnSignup.setEnabled(true);
                btnSignup.setAlpha(1.0f);

                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Toast.makeText(signup.this, "Registration successful! Please login.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(signup.this, login.class));
                    finish();
                } else {
                    String error = "Registration failed";
                    if (response.body() != null && response.body().getError() != null) {
                        error = response.body().getError();
                    } else if (response.code() == 409) {
                        error = "Email already registered";
                    }
                    Toast.makeText(signup.this, error, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<AuthResponse.UserData>> call, Throwable t) {
                btnSignup.setEnabled(true);
                btnSignup.setAlpha(1.0f);
                Toast.makeText(signup.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private String mapRole(String displayRole) {
        if (displayRole == null) return "student";
        switch (displayRole) {
            case "Teacher": return "faculty";
            case "Admin": return "admin";
            default: return "student";
        }
    }

    private boolean validateInputs(String name, String email, String pass, String confPass) {
        boolean valid = true;

        if (TextUtils.isEmpty(name)) { tilName.setError("Name is required"); valid = false; }
        else tilName.setError(null);

        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Valid email is required");
            valid = false;
        } else tilEmail.setError(null);

        if (pass.length() < 8) { tilPass.setError("Minimum 8 characters"); valid = false; }
        else tilPass.setError(null);

        if (!pass.equals(confPass)) { tilConfPass.setError("Passwords do not match"); valid = false; }
        else tilConfPass.setError(null);

        return valid;
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
