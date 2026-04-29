package com.example.firstapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.api.ApiClient;
import com.example.firstapp.api.ApiResponse;
import com.example.firstapp.api.service.AuthService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class forgot_password extends AppCompatActivity {

    private TextInputLayout tilEmail;
    private TextInputEditText etEmail;
    private View btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        tilEmail = findViewById(R.id.email);
        etEmail = (TextInputEditText) tilEmail.getEditText();
        btnContinue = findViewById(R.id.confirm_button);
    }

    private void setupClickListeners() {
        btnContinue.setOnClickListener(v -> {
            hideKeyboard();
            String email = etEmail.getText().toString().trim();
            if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                tilEmail.setError("Enter a valid email address");
                return;
            }

            tilEmail.setError(null);
            btnContinue.setEnabled(false);
            btnContinue.setAlpha(0.5f);

            AuthService authService = ApiClient.getInstance(this).create(AuthService.class);
            AuthService.ForgotPasswordRequest request = new AuthService.ForgotPasswordRequest(email);
            authService.forgotPassword(request).enqueue(new Callback<ApiResponse<Void>>() {
                @Override
                public void onResponse(Call<ApiResponse<Void>> call,
                                       Response<ApiResponse<Void>> response) {
                    btnContinue.setEnabled(true);
                    btnContinue.setAlpha(1.0f);

                    if (response.isSuccessful()) {
                        Toast.makeText(forgot_password.this, "If an account exists, a reset link has been sent.", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(forgot_password.this, login.class));
                        finish();
                    } else {
                        Toast.makeText(forgot_password.this, "Something went wrong. Try again.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                    btnContinue.setEnabled(true);
                    btnContinue.setAlpha(1.0f);
                    Toast.makeText(forgot_password.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        findViewById(R.id.main).setOnClickListener(v -> hideKeyboard());
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
