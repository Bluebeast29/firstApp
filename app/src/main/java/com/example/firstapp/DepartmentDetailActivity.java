package com.example.firstapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.models.Course;
import com.example.firstapp.models.Department;
import com.example.firstapp.models.Faculty;

import java.util.ArrayList;
import java.util.List;

public class DepartmentDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_detail);

        // Assuming CS is passed for demonstration
        Department dept = MockData.getDepartments().get(0);

        TextView deptNameText = findViewById(R.id.deptNameText);
        TextView studentCountText = findViewById(R.id.studentCountText);
        ListView facultyListView = findViewById(R.id.facultyListView);
        ListView courseListView = findViewById(R.id.courseListView);

        deptNameText.setText(dept.name + " (" + dept.code + ")");
        studentCountText.setText("Total Students: " + dept.totalStudents);

        List<Faculty> allFaculty = MockData.getFaculty();
        List<String> facultyStrings = new ArrayList<>();
        for (Faculty f : allFaculty) {
            if (f.department.equals(dept.name)) {
                facultyStrings.add(f.name + " - " + f.designation);
            }
        }
        ArrayAdapter<String> facultyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, facultyStrings);
        facultyListView.setAdapter(facultyAdapter);

        List<Course> allCourses = MockData.getCourses();
        List<String> courseStrings = new ArrayList<>();
        for (Course c : allCourses) {
            if (c.department.equals(dept.name)) {
                courseStrings.add(c.name + " (" + c.code + ")");
            }
        }
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseStrings);
        courseListView.setAdapter(courseAdapter);
    }
}
