package com.example.firstapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.api.ApiClient;
import com.example.firstapp.api.ApiResponse;
import com.example.firstapp.api.dto.CourseDto;
import com.example.firstapp.api.dto.DepartmentDto;
import com.example.firstapp.api.dto.FacultyDto;
import com.example.firstapp.api.dto.SessionDto;
import com.example.firstapp.api.dto.StudentDto;
import com.example.firstapp.api.service.DataService;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenericListActivity extends AppCompatActivity {

    private List<String> originalList;
    private List<String> filteredList;
    private GenericAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_generic);

        String type = getIntent().getStringExtra("LIST_TYPE");
        if (type != null) {
            ((TextView) findViewById(R.id.tv_list_title)).setText(type);
        }

        originalList = new ArrayList<>();
        filteredList = new ArrayList<>();

        setupRecyclerView();
        setupSearch();
        loadData(type);
    }

    private void loadData(String type) {
        DataService dataService = ApiClient.getInstance(this).create(DataService.class);

        if ("Subjects".equals(type) || "Courses".equals(type)) {
            dataService.getCourses().enqueue(new Callback<ApiResponse<List<CourseDto>>>() {
                @Override
                public void onResponse(Call<ApiResponse<List<CourseDto>>> call,
                                       Response<ApiResponse<List<CourseDto>>> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                        List<CourseDto> courses = response.body().getData();
                        if (courses != null) {
                            for (CourseDto c : courses) {
                                originalList.add(c.getName() + " (" + c.getCode() + ") - " + c.getCredits() + " credits");
                            }
                        }
                    }
                    if (originalList.isEmpty()) originalList.add("No courses found");
                    filteredList.addAll(originalList);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<ApiResponse<List<CourseDto>>> call, Throwable t) {
                    originalList.add("Failed to load courses");
                    filteredList.addAll(originalList);
                    adapter.notifyDataSetChanged();
                }
            });
        } else if ("Students".equals(type)) {
            dataService.getStudents().enqueue(new Callback<ApiResponse<List<StudentDto>>>() {
                @Override
                public void onResponse(Call<ApiResponse<List<StudentDto>>> call,
                                       Response<ApiResponse<List<StudentDto>>> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                        List<StudentDto> students = response.body().getData();
                        if (students != null) {
                            for (StudentDto s : students) {
                                String name = s.getName() != null ? s.getName() : "Student";
                                originalList.add(name + " (" + s.getRollNumber() + ")");
                            }
                        }
                    }
                    if (originalList.isEmpty()) originalList.add("No students found");
                    filteredList.addAll(originalList);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<ApiResponse<List<StudentDto>>> call, Throwable t) {
                    originalList.add("Failed to load students");
                    filteredList.addAll(originalList);
                    adapter.notifyDataSetChanged();
                }
            });
        } else if ("Departments".equals(type)) {
            dataService.getDepartments().enqueue(new Callback<ApiResponse<List<DepartmentDto>>>() {
                @Override
                public void onResponse(Call<ApiResponse<List<DepartmentDto>>> call,
                                       Response<ApiResponse<List<DepartmentDto>>> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                        List<DepartmentDto> depts = response.body().getData();
                        if (depts != null) {
                            for (DepartmentDto d : depts) {
                                originalList.add(d.getName() + " (" + d.getCode() + ")");
                            }
                        }
                    }
                    if (originalList.isEmpty()) originalList.add("No departments found");
                    filteredList.addAll(originalList);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<ApiResponse<List<DepartmentDto>>> call, Throwable t) {
                    originalList.add("Failed to load departments");
                    filteredList.addAll(originalList);
                    adapter.notifyDataSetChanged();
                }
            });
        } else if ("Faculty".equals(type)) {
            dataService.getFaculty().enqueue(new Callback<ApiResponse<List<FacultyDto>>>() {
                @Override
                public void onResponse(Call<ApiResponse<List<FacultyDto>>> call,
                                       Response<ApiResponse<List<FacultyDto>>> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                        List<FacultyDto> faculty = response.body().getData();
                        if (faculty != null) {
                            for (FacultyDto f : faculty) {
                                String name = f.getName() != null ? f.getName() : "Faculty";
                                String desig = f.getDesignation() != null ? f.getDesignation() : "";
                                originalList.add(name + " - " + desig);
                            }
                        }
                    }
                    if (originalList.isEmpty()) originalList.add("No faculty found");
                    filteredList.addAll(originalList);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<ApiResponse<List<FacultyDto>>> call, Throwable t) {
                    originalList.add("Failed to load faculty");
                    filteredList.addAll(originalList);
                    adapter.notifyDataSetChanged();
                }
            });
        } else if ("Schedule".equals(type)) {
            dataService.getSessions().enqueue(new Callback<ApiResponse<List<SessionDto>>>() {
                @Override
                public void onResponse(Call<ApiResponse<List<SessionDto>>> call,
                                       Response<ApiResponse<List<SessionDto>>> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                        List<SessionDto> sessions = response.body().getData();
                        if (sessions != null) {
                            for (SessionDto s : sessions) {
                                String courseName = s.getCourseName() != null ? s.getCourseName() : "Session";
                                String location = s.getLocation() != null ? s.getLocation() : "";
                                originalList.add(courseName + "\n" + s.getDate() + " " + s.getStartTime() + "-" + s.getEndTime() + " | " + location);
                            }
                        }
                    }
                    if (originalList.isEmpty()) originalList.add("No sessions found");
                    filteredList.addAll(originalList);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<ApiResponse<List<SessionDto>>> call, Throwable t) {
                    originalList.add("Failed to load schedule");
                    filteredList.addAll(originalList);
                    adapter.notifyDataSetChanged();
                }
            });
        } else {
            for (int i = 1; i <= 3; i++) originalList.add(type + " Item " + i);
            filteredList.addAll(originalList);
            adapter.notifyDataSetChanged();
        }
    }

    private void setupRecyclerView() {
        RecyclerView rv = findViewById(R.id.rv_generic);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GenericAdapter(filteredList);
        rv.setAdapter(adapter);
    }

    private void setupSearch() {
        EditText etSearch = ((TextInputLayout) findViewById(R.id.search_layout)).getEditText();
        if (etSearch == null) return;

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void filter(String query) {
        filteredList.clear();
        for (String item : originalList) {
            if (item.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
