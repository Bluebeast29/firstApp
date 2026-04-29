package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

public class StudentListActivity extends AppCompatActivity {

    private List<StudentDto> studentDtos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        ListView studentsListView = findViewById(R.id.studentsListView);

        DataService dataService = ApiClient.getInstance(this).create(DataService.class);
        dataService.getStudents().enqueue(new Callback<ApiResponse<List<StudentDto>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<StudentDto>>> call,
                                   Response<ApiResponse<List<StudentDto>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    studentDtos = response.body().getData();
                    List<String> studentStrings = new ArrayList<>();
                    if (studentDtos != null) {
                        for (StudentDto student : studentDtos) {
                            String name = student.getName() != null ? student.getName() : "Student";
                            studentStrings.add(name + " (" + student.getRollNumber() + ")\nBatch: " + student.getBatch() + " | Sem: " + student.getSemester());
                        }
                    }
                    if (studentStrings.isEmpty()) studentStrings.add("No students found");
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(StudentListActivity.this, android.R.layout.simple_list_item_1, studentStrings);
                    studentsListView.setAdapter(adapter);
                } else {
                    String error = "Error loading directory";
                    if (response.code() == 401) error = "Session expired. Please login again.";
                    else if (response.body() != null && response.body().getMessage() != null) error = response.body().getMessage();
                    Toast.makeText(StudentListActivity.this, error, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<StudentDto>>> call, Throwable t) {
                Toast.makeText(StudentListActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        studentsListView.setOnItemClickListener((parent, view, position, id) -> {
            if (position < studentDtos.size()) {
                Intent intent = new Intent(StudentListActivity.this, StudentProfileActivity.class);
                intent.putExtra("STUDENT_ID", studentDtos.get(position).getId());
                startActivity(intent);
            }
        });
    }
}
