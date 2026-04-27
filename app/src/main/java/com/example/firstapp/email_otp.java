package com.example.firstapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;

public class email_otp extends AppCompatActivity {

    private TextInputLayout tilOtp;
    private EditText etOtp;
    private View btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_otp);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        tilOtp = findViewById(R.id.input_enroll);
        etOtp = tilOtp.getEditText();
        btnContinue = findViewById(R.id.cotinue_button);
        
        if (etOtp != null) etOtp.setText("123456"); // Dummy data
    }

    private void setupClickListeners() {
        btnContinue.setOnClickListener(v -> {
            hideKeyboard();
            if (etOtp == null) return;
            
            String otp = etOtp.getText().toString().trim();
            if (TextUtils.isEmpty(otp) || otp.length() < 4) {
                tilOtp.setError("Enter a valid verification code");
                return;
            }
            
            tilOtp.setError(null);
            btnContinue.setEnabled(false);
            btnContinue.setAlpha(0.5f);
            
            Toast.makeText(this, "Verification successful!", Toast.LENGTH_SHORT).show();
            
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(this, login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }, 1000);
        });

        findViewById(R.id.chnage_id).setOnClickListener(v -> finish());
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