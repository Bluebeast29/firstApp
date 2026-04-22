package com.example.firstapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.models.Faculty;

public class FacultyProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_profile);

        // Assume showing the first faculty
        Faculty faculty = MockData.getFaculty().get(0);

        TextView facultyNameText = findViewById(R.id.facultyNameText);
        TextView facultyEmailText = findViewById(R.id.facultyEmailText);
        TextView facultyInfoText = findViewById(R.id.facultyInfoText);
        TextView facultySpecializationText = findViewById(R.id.facultySpecializationText);
        ListView teachingLoadListView = findViewById(R.id.teachingLoadListView);

        facultyNameText.setText(faculty.name);
        facultyEmailText.setText(faculty.email);
        facultyInfoText.setText("Dept: " + faculty.department + " | Designation: " + faculty.designation);
        facultySpecializationText.setText(faculty.specialization);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, faculty.teachingLoad);
        teachingLoadListView.setAdapter(adapter);
    }
}
