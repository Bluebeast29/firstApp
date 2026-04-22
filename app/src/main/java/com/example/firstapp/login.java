package com.example.firstapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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

        TextView textView = findViewById(R.id.signupText);

        String text = "Don't have an account? Sign up";

        SpannableString spannable = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent signup = new Intent(login.this, signup.class);
                startActivity(signup);
            }
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(Color.parseColor("#2F6AE2"));       // keep color
                ds.setUnderlineText(false);    // remove underline
            }
        };

        TextView forgot = findViewById(R.id.forgot_password);
        forgot.setOnClickListener(v -> {
            Intent forgotPassword = new Intent(login.this, forgot_password.class);
            startActivity(forgotPassword);
        });

        Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(v -> {
            Intent login = new Intent(login.this, home.class);
            login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(login);
        });

        spannable.setSpan(clickableSpan, 23, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannable);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        int enroll = R.id.input_enroll;
        int pass = R.id.input_pass;
        hintText(enroll);
        hintText(pass);
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