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
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

public class signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_signup);

        TextView textView = findViewById(R.id.loginText);

        String text = "Already have an account? Log in";

        SpannableString spannable = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                finish();
            }
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(Color.parseColor("#2F6AE2"));       // keep color
                ds.setUnderlineText(false);    // remove underline
            }
        };

        spannable.setSpan(clickableSpan, 25, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannable);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        int enroll = R.id.enroll;
        int pass = R.id.pass;
        int email = R.id.email;
        int confPass = R.id.confPass;
        hintText(enroll);
        hintText(pass);
        hintText(email);
        hintText(confPass);

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

        Button signUp = findViewById(R.id.signUpButton);
        signUp.setOnClickListener(v -> {
            Intent signup = new Intent(signup.this, email_otp.class);
            startActivity(signup);
        });
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
                textBox.setActivated(!s.toString().trim().isEmpty());
            }
        });
    }
}