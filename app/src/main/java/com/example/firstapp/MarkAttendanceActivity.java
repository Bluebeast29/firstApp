package com.example.firstapp;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MarkAttendanceActivity extends AppCompatActivity {
    
    private StudentAdapter adapter;
    private List<Student> students;
    private boolean isAllSelected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);

        String subject = getIntent().getStringExtra("SUBJECT");
        if (subject != null) {
            // ((TextView)findViewById(R.id.tv_subject)).setText(subject); // Need ID?
        }

        setupRecyclerView();
        setupClickListeners();
    }

    private void setupRecyclerView() {
        RecyclerView rvStudents = findViewById(R.id.rv_students);
        rvStudents.setLayoutManager(new LinearLayoutManager(this));
        
        students = new ArrayList<>();
        students.add(new Student("John Doe", "210101001"));
        students.add(new Student("Jane Smith", "210101002"));
        students.add(new Student("Mike Johnson", "210101003"));
        students.add(new Student("Sarah Williams", "210101004"));
        students.add(new Student("Robert Brown", "210101005"));
        students.add(new Student("Emily Davis", "210101006"));
        students.add(new Student("Chris Wilson", "210101007"));
        students.add(new Student("Jessica Taylor", "210101008"));
        students.add(new Student("David Miller", "210101009"));
        students.add(new Student("Ashley Moore", "210101010"));

        adapter = new StudentAdapter(students);
        rvStudents.setAdapter(adapter);
        
        TextView tvCount = findViewById(R.id.tv_count);
        tvCount.setText(String.format("%d Students Loaded", students.size()));
    }

    private void setupClickListeners() {
        Button btnSelectAll = findViewById(R.id.btn_select_all);
        btnSelectAll.setOnClickListener(v -> {
            isAllSelected = !isAllSelected;
            adapter.selectAll(isAllSelected);
            btnSelectAll.setText(isAllSelected ? "Deselect All" : "Select All");
        });

        findViewById(R.id.btn_save).setOnClickListener(v -> {
            v.setEnabled(false);
            Toast.makeText(this, "Submitting Attendance...", Toast.LENGTH_SHORT).show();
            
            new Handler().postDelayed(() -> {
                Toast.makeText(this, "Attendance Saved Successfully!", Toast.LENGTH_LONG).show();
                finish();
            }, 1500);
        });
    }
}