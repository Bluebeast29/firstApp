package com.example.firstapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.models.Session;
import com.example.firstapp.models.Student;

import java.util.ArrayList;
import java.util.List;

public class MarkAttendanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);

        TextView sessionInfoText = findViewById(R.id.sessionInfoText);
        ListView studentsAttendanceListView = findViewById(R.id.studentsAttendanceListView);
        Button saveAttendanceButton = findViewById(R.id.saveAttendanceButton);

        // Assume checking first session
        Session session = MockData.getSessions().get(0);
        sessionInfoText.setText(session.courseName + "\n" + session.date + " " + session.startTime);

        List<Student> students = MockData.getStudents();
        List<String> studentStrings = new ArrayList<>();
        for (Student s : students) {
            studentStrings.add(s.name + " (" + s.rollNumber + ")\n[Present / Absent / Late / Excused]");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, studentStrings);
        studentsAttendanceListView.setAdapter(adapter);
        studentsAttendanceListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        saveAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MarkAttendanceActivity.this, "Attendance Saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
