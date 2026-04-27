package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.models.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        ListView studentsListView = findViewById(R.id.studentsListView);

        List<Student> students = MockData.getStudents();
        List<String> studentStrings = new ArrayList<>();
        for (Student student : students) {
            studentStrings.add(student.name + " (" + student.rollNumber + ")\n" + student.department + " | Batch: " + student.batch + " | Sem: " + student.semester);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentStrings);
        studentsListView.setAdapter(adapter);

        studentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StudentListActivity.this, StudentProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
