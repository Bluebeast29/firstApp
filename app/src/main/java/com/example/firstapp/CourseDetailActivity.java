package com.example.firstapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.models.Course;

public class CourseDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        // Assume showing the first course
        Course course = MockData.getCourses().get(0);

        TextView courseNameText = findViewById(R.id.courseNameText);
        TextView courseInfoText = findViewById(R.id.courseInfoText);
        TextView courseDescriptionText = findViewById(R.id.courseDescriptionText);
        ListView prerequisitesListView = findViewById(R.id.prerequisitesListView);
        ListView offeringsListView = findViewById(R.id.offeringsListView);

        courseNameText.setText(course.name + " (" + course.code + ")");
        courseInfoText.setText("Credits: " + course.credits + " | Level: " + course.level);
        courseDescriptionText.setText(course.description);

        ArrayAdapter<String> prereqAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, course.prerequisites);
        prerequisitesListView.setAdapter(prereqAdapter);

        ArrayAdapter<String> offeringsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, course.currentOfferings);
        offeringsListView.setAdapter(offeringsAdapter);
    }
}
