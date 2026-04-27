package com.example.firstapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MyAttendanceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_attendance);

        RecyclerView rvAttendance = findViewById(R.id.rv_attendance);
        rvAttendance.setLayoutManager(new LinearLayoutManager(this));

        List<String> attendanceData = new ArrayList<>();
        attendanceData.add("Data Analysis & Algorithms: 92%");
        attendanceData.add("Discrete Mathematics: 88%");
        attendanceData.add("Database Management: 85%");
        attendanceData.add("Computer Networks: 78%");
        attendanceData.add("Operating Systems: 90%");
        attendanceData.add("Software Engineering: 82%");

        GenericAdapter adapter = new GenericAdapter(attendanceData);
        rvAttendance.setAdapter(adapter);
    }
}