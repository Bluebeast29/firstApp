package com.example.firstapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.api.ApiClient;
import com.example.firstapp.api.ApiResponse;
import com.example.firstapp.api.dto.EnrollmentDto;
import com.example.firstapp.api.dto.StudentDto;
import com.example.firstapp.api.service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarkAttendanceActivity extends AppCompatActivity {

    private StudentAdapter adapter;
    private List<Student> students;
    private boolean isAllSelected = true;
    private String offeringId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);

        String subject = getIntent().getStringExtra("SUBJECT");
        offeringId = getIntent().getStringExtra("OFFERING_ID");

        int idSubject = getResources().getIdentifier("tv_subject", "id", getPackageName());
        if (idSubject != 0) { TextView tvSubject = findViewById(idSubject); if (tvSubject != null && subject != null) tvSubject.setText(subject); }

        setupRecyclerView();
        setupClickListeners();
        loadStudents();
    }

    private void setupRecyclerView() {
        RecyclerView rvStudents = findViewById(R.id.rv_students);
        rvStudents.setLayoutManager(new LinearLayoutManager(this));
        students = new ArrayList<>();
        adapter = new StudentAdapter(students);
        rvStudents.setAdapter(adapter);
    }

    private void loadStudents() {
        TextView tvCount = findViewById(R.id.tv_count);
        tvCount.setText("Loading students...");

        DataService dataService = ApiClient.getInstance(this).create(DataService.class);

        dataService.getStudents().enqueue(new Callback<ApiResponse<List<StudentDto>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<StudentDto>>> call,
                                   Response<ApiResponse<List<StudentDto>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    List<StudentDto> dtos = response.body().getData();
                    students.clear();
                    if (dtos != null) {
                        for (StudentDto dto : dtos) {
                            String name = dto.getName() != null ? dto.getName() : "Student";
                            String roll = dto.getRollNumber() != null ? dto.getRollNumber() : dto.getId();
                            students.add(new Student(name, roll, dto.getId()));
                        }
                    }
                    adapter.notifyDataSetChanged();
                    tvCount.setText(String.format("%d Students Loaded", students.size()));
                } else {
                    tvCount.setText("No students found");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<StudentDto>>> call, Throwable t) {
                tvCount.setText("Failed to load students");
            }
        });
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

            List<DataService.AttendanceEntry> entries = new ArrayList<>();
            for (Student s : students) {
                entries.add(new DataService.AttendanceEntry(s.getStudentId(), s.isPresent() ? "present" : "absent"));
            }

            DataService dataService = ApiClient.getInstance(this).create(DataService.class);
            DataService.MarkAttendanceRequest request = new DataService.MarkAttendanceRequest(null, entries);
            dataService.markAttendance(request).enqueue(new Callback<ApiResponse<List<com.example.firstapp.api.dto.AttendanceDto>>>() {
                @Override
                public void onResponse(Call<ApiResponse<List<com.example.firstapp.api.dto.AttendanceDto>>> call,
                                       Response<ApiResponse<List<com.example.firstapp.api.dto.AttendanceDto>>> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                        Toast.makeText(MarkAttendanceActivity.this, "Attendance Saved Successfully!", Toast.LENGTH_LONG).show();
                    } else {
                        String err = "Failed to save";
                        if (response.body() != null && response.body().getError() != null) err = response.body().getError();
                        Toast.makeText(MarkAttendanceActivity.this, err, Toast.LENGTH_LONG).show();
                    }
                    finish();
                }

                @Override
                public void onFailure(Call<ApiResponse<List<com.example.firstapp.api.dto.AttendanceDto>>> call, Throwable t) {
                    Toast.makeText(MarkAttendanceActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    v.setEnabled(true);
                }
            });
        });
    }
}
