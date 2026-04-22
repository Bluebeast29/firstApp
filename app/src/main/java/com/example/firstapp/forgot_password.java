package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

public class forgot_password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_forgot_password);
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

        Button submit = findViewById(R.id.confirm_button);
        submit.setOnClickListener(v -> {
            Intent next = new Intent(forgot_password.this, email_otp.class);
            startActivity(next);
        });

        int email = R.id.email;
        hintText(email);
    }

    private void hintText(int name) {
        TextInputLayout textBox = findViewById(name);
        EditText field = textBox.getEditText();
        LinearLayout main = findViewById(R.id.mainCard);

        field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().isEmpty()) {
                    textBox.setActivated(false);
                } else{
                    textBox.setActivated(true);
                }
            }
        });
    }
}