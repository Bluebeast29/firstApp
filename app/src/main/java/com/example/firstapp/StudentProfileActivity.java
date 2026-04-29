package com.example.firstapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.api.ApiClient;
import com.example.firstapp.api.ApiResponse;
import com.example.firstapp.api.dto.StudentDto;
import com.example.firstapp.api.service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        String studentId = getIntent().getStringExtra("STUDENT_ID");
        if (studentId == null) {
            showEmpty();
            return;
        }

        DataService dataService = ApiClient.getInstance(this).create(DataService.class);
        dataService.getStudent(studentId).enqueue(new Callback<ApiResponse<StudentDto>>() {
            @Override
            public void onResponse(Call<ApiResponse<StudentDto>> call,
                                   Response<ApiResponse<StudentDto>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    StudentDto student = response.body().getData();
                    if (student != null) {
                        String name = student.getName() != null ? student.getName() : "Student";
                        String email = student.getEmail() != null ? student.getEmail() : "";

                        ((TextView) findViewById(R.id.studentNameText)).setText(name);
                        ((TextView) findViewById(R.id.studentEmailText)).setText(email);
                        ((TextView) findViewById(R.id.studentInfoText)).setText(
                                "Roll No: " + student.getRollNumber() + " | Batch: " + student.getBatch() + " | Sem: " + student.getSemester());
                        String deptName = student.getDepartment() != null ? student.getDepartment().getName() : "N/A";
                        ((TextView) findViewById(R.id.studentAttendanceText)).setText("Department: " + deptName);

                        List<String> placeholder = new ArrayList<>();
                        placeholder.add("Enrolled courses loading...");
                        ArrayAdapter<String> coursesAdapter = new ArrayAdapter<>(StudentProfileActivity.this, android.R.layout.simple_list_item_1, placeholder);
                        ((ListView) findViewById(R.id.enrolledCoursesListView)).setAdapter(coursesAdapter);
                    }
                } else {
                    showEmpty();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<StudentDto>> call, Throwable t) {
                showEmpty();
            }
        });
    }

    private void showEmpty() {
        TextView nameText = findViewById(R.id.studentNameText);
        if (nameText != null) nameText.setText("Student not found");
    }
}
