package com.example.firstapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.models.Session;

import java.util.ArrayList;
import java.util.List;

public class SessionsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions_list);

        ListView sessionsListView = findViewById(R.id.sessionsListView);

        List<Session> sessions = MockData.getSessions();
        List<String> sessionStrings = new ArrayList<>();
        for (Session s : sessions) {
            sessionStrings.add(s.courseName + "\n" + s.date + " " + s.startTime + " - " + s.endTime + "\n" + s.room + " | Faculty: " + s.facultyName + "\nStatus: " + s.status);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sessionStrings);
        sessionsListView.setAdapter(adapter);
    }
}
