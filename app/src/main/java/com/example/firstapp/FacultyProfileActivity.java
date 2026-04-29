package com.example.firstapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.api.ApiClient;
import com.example.firstapp.api.ApiResponse;
import com.example.firstapp.api.dto.FacultyDto;
import com.example.firstapp.api.dto.OfferingDto;
import com.example.firstapp.api.service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FacultyProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_profile);

        String facultyId = getIntent().getStringExtra("FACULTY_ID");
        if (facultyId == null) {
            ((TextView) findViewById(R.id.facultyNameText)).setText("Faculty ID missing");
            return;
        }

        DataService dataService = ApiClient.getInstance(this).create(DataService.class);
        dataService.getFacultyById(facultyId).enqueue(new Callback<ApiResponse<FacultyDto>>() {
            @Override
            public void onResponse(Call<ApiResponse<FacultyDto>> call,
                                   Response<ApiResponse<FacultyDto>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    FacultyDto faculty = response.body().getData();
                    if (faculty != null) {
                        displayFaculty(faculty);
                        loadTeachingLoad(dataService, facultyId);
                    }
                } else {
                    String error = "Faculty not found";
                    if (response.code() == 401) error = "Session expired";
                    Toast.makeText(FacultyProfileActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<FacultyDto>> call, Throwable t) {
                Toast.makeText(FacultyProfileActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayFaculty(FacultyDto faculty) {
        String name = faculty.getName() != null ? faculty.getName() : "Faculty";
        String email = faculty.getEmail() != null ? faculty.getEmail() : "";
        String designation = faculty.getDesignation() != null ? faculty.getDesignation() : "";

        ((TextView) findViewById(R.id.facultyNameText)).setText(name);
        ((TextView) findViewById(R.id.facultyEmailText)).setText(email);
        ((TextView) findViewById(R.id.facultyInfoText)).setText("Designation: " + designation);
        ((TextView) findViewById(R.id.facultySpecializationText)).setText(faculty.getSpecialization());
    }

    private void loadTeachingLoad(DataService dataService, String facultyId) {
        dataService.getFacultyOfferings(facultyId).enqueue(new Callback<ApiResponse<List<OfferingDto>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<OfferingDto>>> call,
                                   Response<ApiResponse<List<OfferingDto>>> response) {
                List<String> loadStrings = new ArrayList<>();
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    List<OfferingDto> offerings = response.body().getData();
                    if (offerings != null) {
                        for (OfferingDto o : offerings) {
                            String cName = o.getCourseName() != null ? o.getCourseName() : "Course";
                            String sec = o.getSection() != null ? " (" + o.getSection() + ")" : "";
                            loadStrings.add(cName + sec);
                        }
                    }
                }
                if (loadStrings.isEmpty()) loadStrings.add("No teaching assignments");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(FacultyProfileActivity.this, android.R.layout.simple_list_item_1, loadStrings);
                ((ListView) findViewById(R.id.teachingLoadListView)).setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ApiResponse<List<OfferingDto>>> call, Throwable t) {}
        });
    }
}
