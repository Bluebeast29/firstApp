package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.api.ApiClient;
import com.example.firstapp.api.ApiResponse;
import com.example.firstapp.api.dto.DepartmentDto;
import com.example.firstapp.api.service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DepartmentListActivity extends AppCompatActivity {

    private List<DepartmentDto> deptDtos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_list);

        ListView departmentsListView = findViewById(R.id.departmentsListView);

        DataService dataService = ApiClient.getInstance(this).create(DataService.class);
        dataService.getDepartments().enqueue(new Callback<ApiResponse<List<DepartmentDto>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<DepartmentDto>>> call,
                                   Response<ApiResponse<List<DepartmentDto>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    deptDtos = response.body().getData();
                    List<String> deptStrings = new ArrayList<>();
                    if (deptDtos != null) {
                        for (DepartmentDto dept : deptDtos) {
                            deptStrings.add(dept.getName() + " (" + dept.getCode() + ")");
                        }
                    }
                    if (deptStrings.isEmpty()) deptStrings.add("No departments found");
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(DepartmentListActivity.this, android.R.layout.simple_list_item_1, deptStrings);
                    departmentsListView.setAdapter(adapter);
                } else {
                    String error = "Error loading departments";
                    if (response.body() != null && response.body().getMessage() != null) {
                        error = response.body().getMessage();
                    } else if (response.code() == 401) {
                        error = "Session expired. Please login again.";
                    }
                    Toast.makeText(DepartmentListActivity.this, error, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<DepartmentDto>>> call, Throwable t) {
                Toast.makeText(DepartmentListActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        departmentsListView.setOnItemClickListener((parent, view, position, id) -> {
            if (position < deptDtos.size()) {
                Intent intent = new Intent(DepartmentListActivity.this, DepartmentDetailActivity.class);
                intent.putExtra("DEPARTMENT_ID", deptDtos.get(position).getId());
                startActivity(intent);
            }
        });
    }
}
