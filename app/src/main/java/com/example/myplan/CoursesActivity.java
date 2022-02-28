package com.example.myplan;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myplan.adapters.CoursesAdapter;
import com.example.myplan.model.Course;

import java.util.ArrayList;

public class CoursesActivity extends AppCompatActivity {

    Toolbar mToolBar;

    CoursesAdapter mAdapter;
    RecyclerView mSubjectsList;
    ArrayList<Course> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        mToolBar = findViewById(R.id.toolbar);
        mSubjectsList = findViewById(R.id.rv_subjects);

        setSupportActionBar(mToolBar);

        courses = new ArrayList<Course>()
        {{
            add(new Course(1, "Object oriented programming"));
            add(new Course(2,"Database management systems"));
            add(new Course(3, "Data structure and algorithms"));
            add(new Course(4, "Operating system"));
            add(new Course(5, "Microprocessor and assembly language"));
            add(new Course(6, "Artificial intelligence"));
            add(new Course(7, "Programming with python"));
            add(new Course(8, "Design and analysis of algorithms"));
        }};

        mAdapter = new CoursesAdapter(this, courses);
        mSubjectsList.setAdapter(mAdapter);
    }

    //todo api call
    void getAllSubjects(){

    }
}