package com.example.firstapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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
            
            Toast.makeText(this, "Verification code sent!", Toast.LENGTH_SHORT).show();
            
            new Handler().postDelayed(() -> {
                startActivity(new Intent(this, email_otp.class));
                finish();
            }, 1000);
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