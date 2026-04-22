package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class email_otp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_email_otp);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_email_otp);
        View root = findViewById(R.id.mainCard);
        root.setOnClickListener(v -> {
            // clear focus
            View current = getCurrentFocus();
            if (current != null) {
                current.clearFocus();
            }

            // hide keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (imm != null && current != null) {
                imm.hideSoftInputFromWindow(current.getWindowToken(), 0);
            }
        });

        Button contine = findViewById(R.id.cotinue_button);
        contine.setOnClickListener(v -> {
            Intent login = new Intent(email_otp.this, login.class);
            login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(login);
            finish();
        });

        TextView text = findViewById(R.id.chnage_id);
        text.setOnClickListener(v ->{
            finish();
        });
    }
}