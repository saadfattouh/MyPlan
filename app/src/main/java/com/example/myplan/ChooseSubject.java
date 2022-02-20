package com.example.myplan;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myplan.adapters.SubjectsAdapter;
import com.example.myplan.model.Subject;

import java.util.ArrayList;

public class ChooseSubject extends AppCompatActivity {

    Toolbar mToolBar;

    SubjectsAdapter mAdapter;
    RecyclerView mSubjectsList;
    ArrayList<Subject> subjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_choose_subject);

        mToolBar = findViewById(R.id.toolbar);
        mSubjectsList = findViewById(R.id.rv);

        mToolBar.setTitle("subjects (experimental names)");

        setSupportActionBar(mToolBar);

        subjects = new ArrayList<Subject>()
        {{
            add(new Subject(1, "statistics"));
            add(new Subject(2,"calculus"));
            add(new Subject(3, "programming"));
            add(new Subject(1, "information security"));
        }};


        mAdapter = new SubjectsAdapter(this, subjects);
        mSubjectsList.setAdapter(mAdapter);



    }

    //todo api call
    void getAllSubjects(){

    }
}