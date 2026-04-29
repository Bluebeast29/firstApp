package com.example.firstapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.api.ApiClient;
import com.example.firstapp.api.ApiResponse;
import com.example.firstapp.api.dto.SessionDto;
import com.example.firstapp.api.service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SessionsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions_list);

        ListView sessionsListView = findViewById(R.id.sessionsListView);

        DataService dataService = ApiClient.getInstance(this).create(DataService.class);
        dataService.getSessions().enqueue(new Callback<ApiResponse<List<SessionDto>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<SessionDto>>> call,
                                   Response<ApiResponse<List<SessionDto>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    List<SessionDto> sessions = response.body().getData();
                    List<String> sessionStrings = new ArrayList<>();
                    if (sessions != null) {
                        for (SessionDto s : sessions) {
                            String courseName = s.getCourseName() != null ? s.getCourseName() : "Session";
                            String facultyName = s.getFacultyName() != null ? s.getFacultyName() : "";
                            String location = s.getLocation() != null ? s.getLocation() : "";
                            sessionStrings.add(courseName + "\n" + s.getDate() + " " + s.getStartTime() + " - " + s.getEndTime() + "\n" + location + " | " + facultyName + "\nStatus: " + s.getStatus());
                        }
                    }
                    if (sessionStrings.isEmpty()) sessionStrings.add("No sessions found");
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(SessionsListActivity.this, android.R.layout.simple_list_item_1, sessionStrings);
                    sessionsListView.setAdapter(adapter);
                } else {
                    String error = "Error loading sessions";
                    if (response.body() != null && response.body().getMessage() != null) {
                        error = response.body().getMessage();
                    }
                    Toast.makeText(SessionsListActivity.this, error, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<SessionDto>>> call, Throwable t) {
                Toast.makeText(SessionsListActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
