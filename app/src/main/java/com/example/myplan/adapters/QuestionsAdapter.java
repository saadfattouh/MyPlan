package com.example.myplan.adapters;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myplan.CourseActivity;
import com.example.myplan.R;
import com.example.myplan.model.Course;
import com.example.myplan.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {


    Context context;
    private List<Question> questions;
    private int marks;
    private ArrayList<String> strengths;
    private ArrayList<String> weaknesses;

    // RecyclerView recyclerView;
    public QuestionsAdapter(Context context, ArrayList<Question> questions) {
        this.context = context;
        this.questions = questions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_question, parent, false);

        return new ViewHolder(listItem);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Question question = questions.get(position);

        holder.number.setText(String.valueOf(position + 1) + ". ");

        holder.question.setText(question.getQuestion());

        addAnswers(holder.answersViewGroup, question.getAnswers());

        holder.answersViewGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selected = group.findViewById(checkedId);
            int selectedIndex = question.getAnswers().indexOf(selected.getText().toString());
            question.setSelectedAnswer(selectedIndex);
        });

    }

    private void addAnswers(RadioGroup answersGroup, ArrayList<String> answers) {
        for (int i = 0; i < answers.size() ; i++) {
            RadioButton rbn = new RadioButton(context);
            rbn.setId(View.generateViewId());
            rbn.setText(answers.get(i));
            rbn.setTextColor(context.getResources().getColor(R.color.black));
            rbn.setAlpha(0.8f);
            answersGroup.addView(rbn);
        }
    }

    private void correctTheExam(){
        strengths = new ArrayList<String>();
        weaknesses = new ArrayList<String>();

        marks = 0;
        for (Question question: questions){
            if(question.getSelectedAnswer() == question.getWriteAnswer()){
                marks++;
                strengths.add(question.getLectureName());
            }else {
                weaknesses.add(question.getLectureName());
            }
        }

    }

    public int getMarks() {
        return marks;
    }

    public ArrayList<String> getStrengths() {
        return strengths;
    }

    public ArrayList<String> getWeaknesses() {
        return weaknesses;
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView number, question;
        public RadioGroup answersViewGroup;

        public ViewHolder(View itemView) {
            super(itemView);
            this.number = itemView.findViewById(R.id.number);
            this.question = itemView.findViewById(R.id.question);
            this.answersViewGroup = itemView.findViewById(R.id.answers_group);
        }
    }


}