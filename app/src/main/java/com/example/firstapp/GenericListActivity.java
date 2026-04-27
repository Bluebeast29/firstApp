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
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.List;

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
            ((TextView)findViewById(R.id.tv_list_title)).setText(type);
        }

        setupLists(type);
        setupRecyclerView();
        setupSearch();
    }

    private void setupLists(String type) {
        originalList = new ArrayList<>();
        if ("Subjects".equals(type) || "Courses".equals(type)) {
            originalList.add("Data Analysis & Algorithms");
            originalList.add("Discrete Mathematics");
            originalList.add("Database Management");
            originalList.add("Computer Networks");
            originalList.add("Operating Systems");
            originalList.add("Software Engineering");
        } else if ("Students".equals(type)) {
            originalList.add("John Doe (210101001)");
            originalList.add("Jane Smith (210101002)");
            originalList.add("Mike Johnson (210101003)");
            originalList.add("Sarah Williams (210101004)");
        } else {
            for(int i=1; i<=10; i++) originalList.add(type + " Item " + i);
        }
        filteredList = new ArrayList<>(originalList);
    }

    private void setupRecyclerView() {
        RecyclerView rv = findViewById(R.id.rv_generic);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GenericAdapter(filteredList);
        rv.setAdapter(adapter);
    }

    private void setupSearch() {
        EditText etSearch = ((TextInputLayout)findViewById(R.id.search_layout)).getEditText();
        if (etSearch == null) return;

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
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