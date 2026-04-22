package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.models.Department;

import java.util.ArrayList;
import java.util.List;

public class signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        TextView loginText = findViewById(R.id.loginText);
        Button signUpButton = findViewById(R.id.signUpButton);
        Spinner roleSpinner = findViewById(R.id.roleSpinner);
        Spinner departmentSpinner = findViewById(R.id.departmentSpinner);

        // Setup Role Spinner
        String[] roles = {"Student", "Faculty", "Admin"};
        ArrayAdapter<String> roleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roles);
        roleSpinner.setAdapter(roleAdapter);

        // Setup Department Spinner
        List<Department> departments = MockData.getDepartments();
        List<String> deptNames = new ArrayList<>();
        for (Department dept : departments) {
            deptNames.add(dept.name);
        }
        ArrayAdapter<String> deptAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, deptNames);
        departmentSpinner.setAdapter(deptAdapter);

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup.this, login.class);
                startActivity(intent);
                finish();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mock registration logic
                Toast.makeText(signup.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(signup.this, login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
