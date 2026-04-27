package com.example.firstapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.models.Student;

public class StudentProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        // Assume showing the first student
        Student student = MockData.getStudents().get(0);

        TextView studentNameText = findViewById(R.id.studentNameText);
        TextView studentEmailText = findViewById(R.id.studentEmailText);
        TextView studentInfoText = findViewById(R.id.studentInfoText);
        TextView studentAttendanceText = findViewById(R.id.studentAttendanceText);
        ListView enrolledCoursesListView = findViewById(R.id.enrolledCoursesListView);

        studentNameText.setText(student.name);
        studentEmailText.setText(student.email);
        studentInfoText.setText("Roll No: " + student.rollNumber + " | Dept: " + student.department + " | Batch: " + student.batch + " | Sem: " + student.semester);
        studentAttendanceText.setText("Overall Attendance: " + student.overallAttendance);

        ArrayAdapter<String> coursesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, student.enrolledCourses);
        enrolledCoursesListView.setAdapter(coursesAdapter);
    }
}
