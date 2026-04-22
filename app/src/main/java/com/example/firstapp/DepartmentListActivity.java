package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.models.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_list);

        ListView departmentsListView = findViewById(R.id.departmentsListView);

        List<Department> departments = MockData.getDepartments();
        List<String> deptStrings = new ArrayList<>();
        for (Department dept : departments) {
            deptStrings.add(dept.name + " (" + dept.code + ")\nFaculty: " + dept.totalFaculty + " | Students: " + dept.totalStudents);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, deptStrings);
        departmentsListView.setAdapter(adapter);

        departmentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DepartmentListActivity.this, DepartmentDetailActivity.class);
                // In a real app, you would pass the ID
                startActivity(intent);
            }
        });
    }
}
