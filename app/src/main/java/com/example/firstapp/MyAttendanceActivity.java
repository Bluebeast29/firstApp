package com.example.firstapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MyAttendanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_attendance);

        TextView overallAttendanceText = findViewById(R.id.overallAttendanceText);
        ListView myAttendanceListView = findViewById(R.id.myAttendanceListView);

        overallAttendanceText.setText("Overall: 85%");

        List<String> attendanceRecords = new ArrayList<>();
        attendanceRecords.add("CS101: 90% (18 / 20 classes)");
        attendanceRecords.add("CS201: 80% (16 / 20 classes)");
        attendanceRecords.add("Math201: 85% (17 / 20 classes)");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, attendanceRecords);
        myAttendanceListView.setAdapter(adapter);
    }
}
