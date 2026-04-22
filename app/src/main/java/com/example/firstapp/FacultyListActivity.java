package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.models.Faculty;

import java.util.ArrayList;
import java.util.List;

public class FacultyListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_list);

        ListView facultyListView = findViewById(R.id.facultyListView);

        List<Faculty> faculties = MockData.getFaculty();
        List<String> facultyStrings = new ArrayList<>();
        for (Faculty faculty : faculties) {
            facultyStrings.add(faculty.name + " (" + faculty.designation + ")\n" + faculty.department + " | " + faculty.email);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, facultyStrings);
        facultyListView.setAdapter(adapter);

        facultyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FacultyListActivity.this, FacultyProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
