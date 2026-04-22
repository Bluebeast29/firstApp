package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.models.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        ListView coursesListView = findViewById(R.id.coursesListView);

        List<Course> courses = MockData.getCourses();
        List<String> courseStrings = new ArrayList<>();
        for (Course course : courses) {
            courseStrings.add(course.name + " (" + course.code + ")\nDept: " + course.department + " | Credits: " + course.credits + " | " + course.level);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseStrings);
        coursesListView.setAdapter(adapter);

        coursesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CourseListActivity.this, CourseDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
