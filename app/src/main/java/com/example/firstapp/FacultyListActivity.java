package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.api.ApiClient;
import com.example.firstapp.api.ApiResponse;
import com.example.firstapp.api.dto.FacultyDto;
import com.example.firstapp.api.service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FacultyListActivity extends AppCompatActivity {

    private List<FacultyDto> facultyDtos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_list);

        ListView facultyListView = findViewById(R.id.facultyListView);

        DataService dataService = ApiClient.getInstance(this).create(DataService.class);
        dataService.getFaculty().enqueue(new Callback<ApiResponse<List<FacultyDto>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<FacultyDto>>> call,
                                   Response<ApiResponse<List<FacultyDto>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    facultyDtos = response.body().getData();
                    List<String> facultyStrings = new ArrayList<>();
                    if (facultyDtos != null) {
                        for (FacultyDto faculty : facultyDtos) {
                            String name = faculty.getName() != null ? faculty.getName() : "Faculty";
                            String designation = faculty.getDesignation() != null ? faculty.getDesignation() : "";
                            String specialization = faculty.getSpecialization() != null ? faculty.getSpecialization() : "";
                            facultyStrings.add(name + " (" + designation + ")\n" + specialization);
                        }
                    }
                    if (facultyStrings.isEmpty()) facultyStrings.add("No faculty found");
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(FacultyListActivity.this, android.R.layout.simple_list_item_1, facultyStrings);
                    facultyListView.setAdapter(adapter);
                } else {
                    String error = "Error loading faculty directory";
                    if (response.code() == 401) error = "Session expired. Please login again.";
                    else if (response.body() != null && response.body().getMessage() != null) error = response.body().getMessage();
                    Toast.makeText(FacultyListActivity.this, error, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<FacultyDto>>> call, Throwable t) {
                Toast.makeText(FacultyListActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        facultyListView.setOnItemClickListener((parent, view, position, id) -> {
            if (position < facultyDtos.size()) {
                Intent intent = new Intent(FacultyListActivity.this, FacultyProfileActivity.class);
                intent.putExtra("FACULTY_ID", facultyDtos.get(position).getId());
                startActivity(intent);
            }
        });
    }
}
