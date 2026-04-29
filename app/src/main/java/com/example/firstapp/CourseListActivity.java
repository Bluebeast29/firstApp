package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.api.ApiClient;
import com.example.firstapp.api.ApiResponse;
import com.example.firstapp.api.dto.CourseDto;
import com.example.firstapp.api.service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseListActivity extends AppCompatActivity {

    private List<CourseDto> courseDtos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        ListView coursesListView = findViewById(R.id.coursesListView);

        DataService dataService = ApiClient.getInstance(this).create(DataService.class);
        dataService.getCourses().enqueue(new Callback<ApiResponse<List<CourseDto>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<CourseDto>>> call,
                                   Response<ApiResponse<List<CourseDto>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    courseDtos = response.body().getData();
                    List<String> courseStrings = new ArrayList<>();
                    if (courseDtos != null) {
                        for (CourseDto course : courseDtos) {
                            String elective = course.isElective() ? " | Elective" : "";
                            courseStrings.add(course.getName() + " (" + course.getCode() + ")\nCredits: " + course.getCredits() + " | " + course.getLevel() + elective);
                        }
                    }
                    if (courseStrings.isEmpty()) courseStrings.add("No courses found");
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(CourseListActivity.this, android.R.layout.simple_list_item_1, courseStrings);
                    coursesListView.setAdapter(adapter);
                } else {
                    String error = "Error loading catalog";
                    if (response.code() == 401) error = "Session expired. Please login again.";
                    else if (response.body() != null && response.body().getMessage() != null) error = response.body().getMessage();
                    Toast.makeText(CourseListActivity.this, error, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<CourseDto>>> call, Throwable t) {
                Toast.makeText(CourseListActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        coursesListView.setOnItemClickListener((parent, view, position, id) -> {
            if (position < courseDtos.size()) {
                Intent intent = new Intent(CourseListActivity.this, CourseDetailActivity.class);
                intent.putExtra("COURSE_ID", courseDtos.get(position).getId());
                startActivity(intent);
            }
        });
    }
}
