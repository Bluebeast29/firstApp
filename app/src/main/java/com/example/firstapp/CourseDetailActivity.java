package com.example.firstapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.api.ApiClient;
import com.example.firstapp.api.ApiResponse;
import com.example.firstapp.api.dto.CourseDto;
import com.example.firstapp.api.dto.OfferingDto;
import com.example.firstapp.api.service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        String courseId = getIntent().getStringExtra("COURSE_ID");
        if (courseId == null) {
            ((TextView) findViewById(R.id.courseNameText)).setText("Course not found");
            return;
        }

        DataService dataService = ApiClient.getInstance(this).create(DataService.class);

        // Fetch course details
        dataService.getCourse(courseId).enqueue(new Callback<ApiResponse<CourseDto>>() {
            @Override
            public void onResponse(Call<ApiResponse<CourseDto>> call,
                                   Response<ApiResponse<CourseDto>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    CourseDto course = response.body().getData();
                    if (course != null) {
                        ((TextView) findViewById(R.id.courseNameText)).setText(course.getName() + " (" + course.getCode() + ")");
                        ((TextView) findViewById(R.id.courseInfoText)).setText("Credits: " + course.getCredits() + " | Level: " + course.getLevel() + (course.isElective() ? " | Elective" : ""));
                        ((TextView) findViewById(R.id.courseDescriptionText)).setText(course.getDescription());

                        // Prerequisites
                        List<String> prereqs = course.getPrerequisites();
                        if (prereqs == null || prereqs.isEmpty()) {
                            prereqs = new ArrayList<>();
                            prereqs.add("None");
                        }
                        ArrayAdapter<String> prereqAdapter = new ArrayAdapter<>(CourseDetailActivity.this, android.R.layout.simple_list_item_1, prereqs);
                        ((ListView) findViewById(R.id.prerequisitesListView)).setAdapter(prereqAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<CourseDto>> call, Throwable t) {}
        });

        // Fetch offerings for this course
        dataService.getCourseOfferings(courseId).enqueue(new Callback<ApiResponse<List<OfferingDto>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<OfferingDto>>> call,
                                   Response<ApiResponse<List<OfferingDto>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    List<OfferingDto> offerings = response.body().getData();
                    List<String> offeringStrings = new ArrayList<>();
                    if (offerings != null) {
                        for (OfferingDto o : offerings) {
                            String facultyName = o.getFacultyName() != null ? o.getFacultyName() : "TBA";
                            String section = o.getSection() != null ? o.getSection() : "";
                            offeringStrings.add("Section: " + section + " | Faculty: " + facultyName);
                        }
                    }
                    if (offeringStrings.isEmpty()) offeringStrings.add("No current offerings");
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(CourseDetailActivity.this, android.R.layout.simple_list_item_1, offeringStrings);
                    ((ListView) findViewById(R.id.offeringsListView)).setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<OfferingDto>>> call, Throwable t) {}
        });
    }
}
