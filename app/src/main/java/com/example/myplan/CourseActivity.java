package com.example.myplan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myplan.adapters.LecturesAdapter;
import com.example.myplan.model.Lecture;

import java.util.ArrayList;

public class CourseActivity extends AppCompatActivity {

    Toolbar mToolbar;

    RecyclerView mLecturersList;
    LecturesAdapter mAdapter;
    ArrayList<Lecture> lectures;

    LinearLayout mQuizBtn;
    ImageView quizLock;

    String course_name;
    int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        courseId = getIntent().getIntExtra("course_id", -1);
        course_name = getIntent().getStringExtra("name");

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(course_name);
        mQuizBtn = findViewById(R.id.quiz_btn);
        quizLock = findViewById(R.id.lock_quiz);

        mLecturersList = findViewById(R.id.rv_lecturers);

        mQuizBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, QuizActivity.class));
        });



    }

    //todo api call
    private void getLectures(int courseId) {
        if(course_name.equals("Data structure and algorithms")){
            lectures = new ArrayList<Lecture>()
            {{
                add(new Lecture(2, "https://drive.google.com/uc?export=download&id=1EB9rLzsYaPE2RxClP8OC5UfKa8lCrbog", "Course Book"));
            }};
        }else if(course_name.equals("Object oriented programming")){
            lectures = new ArrayList<Lecture>()
            {{
                add(new Lecture(2, "https://drive.google.com/uc?export=download&id=1wRNlnFMx2Ahlbp6EtOST0fI6g8f0-FjI", "Course Book"));
            }};
        }else {
            lectures = new ArrayList<Lecture>()
            {{
                add(new Lecture(2, "https://drive.google.com/uc?export=download&id=1EB9rLzsYaPE2RxClP8OC5UfKa8lCrbog", "Course Book 1"));
                add(new Lecture(2, "https://drive.google.com/uc?export=download&id=1wRNlnFMx2Ahlbp6EtOST0fI6g8f0-FjI", "Course Book 2"));
            }};
        }

        mAdapter = new LecturesAdapter(this, lectures);
        mLecturersList.setAdapter(mAdapter);
    }

    //todo update lock state based on adapter getDone() status
    private void updateQuizLock() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLectures(courseId);
        updateQuizLock();
    }
}