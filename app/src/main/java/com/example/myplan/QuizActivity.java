package com.example.myplan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myplan.adapters.QuestionsAdapter;
import com.example.myplan.model.Question;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    QuestionsAdapter mAdapter;
    RecyclerView mList;
    ArrayList<Question> questions;

    //temp for holding answers before assigning them to their question
    ArrayList<String> answers;
    ArrayList<String> answers2;
    ArrayList<String> answers3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mList = findViewById(R.id.rv_questions);

    }

    private void getQuestions() {

        answers = new ArrayList<String >()
        {{
           add("Object");
           add("Math");
           add("String");
           add("Class");
        }};

        answers2 = new ArrayList<String >()
        {{
            add("1");
            add("2");
            add("3");
            add("4");
        }};

        answers3 = new ArrayList<String >()
        {{
            add("ends the program");
            add("breaks the loop which called within");
            add("stops the main thread");
        }};

        questions = new ArrayList<Question>()
        {{
            add(new Question(1, "what is ths parent class of all classes in java?", "OOP", 4, answers, -1, 0));
            add(new Question(1, "for(int i =0; i<3; i++) how much this for statement loops for?", "Control Flow", 4, answers2, -1, 3));
            add(new Question(1, "what is break key word do?", "Control Flow", 3, answers3, -1, 1));
        }};

        mAdapter = new QuestionsAdapter(this, questions);
        mList.setAdapter(mAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getQuestions();
    }
}