package com.example.geradordegradeescolar.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.geradordegradeescolar.R;
import com.example.geradordegradeescolar.ui.view.RecyclerGradeView;

public class RecyclerGradeActivity extends AppCompatActivity {

    private RecyclerGradeView recyclerGradeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_grade);
        setTitle("Grade");
        recyclerGradeView = new RecyclerGradeView(this);
        configuraRecycler();
    }

    private void configuraRecycler() {
        RecyclerView gradeRecycler = findViewById(R.id.gradeRecycler);
        recyclerGradeView.configuraAdapter(gradeRecycler);
    }
}