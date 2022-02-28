package com.example.myplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioRecord;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button mTodoList, mCourses, mReports, mQuizzes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTodoList = findViewById(R.id.todo_btn);
        mCourses = findViewById(R.id.courses_btn);
        mReports = findViewById(R.id.reports_btn);
        mQuizzes = findViewById(R.id.quizzes_btn);

        mTodoList.setOnClickListener(v -> {
            Intent todoList = new Intent(this, TodoListActivity.class);
            startActivity(todoList);
        });

        mCourses.setOnClickListener(v -> {
            Intent courses = new Intent(this, CoursesActivity.class);
            startActivity(courses);
        });

        mReports.setOnClickListener(v -> {
            Intent reports = new Intent(this, ReportsActivity.class);
            startActivity(reports);
        });

        mQuizzes.setOnClickListener(v -> {
            Intent quizzes = new Intent(this, QuizzesActivity.class);
            startActivity(quizzes);
        });
    }
}