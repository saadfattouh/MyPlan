package com.example.myplan.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myplan.CoursesActivity;
import com.example.myplan.LectureViewActivity;
import com.example.myplan.R;
import com.example.myplan.model.Lecture;
import com.example.myplan.utils.SharedPrefManager;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.List;

public class LecturesAdapter extends RecyclerView.Adapter<LecturesAdapter.ViewHolder> {


    Context context;
    private List<Lecture> lectures;

    SharedPrefManager prefManager;

    // RecyclerView recyclerView;
    public LecturesAdapter(Context context, ArrayList<Lecture> lectures) {
        this.context = context;
        this.lectures = lectures;
        prefManager = SharedPrefManager.getInstance(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_lecture, parent, false);

        return new ViewHolder(listItem);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Lecture lecture = lectures.get(position);

        holder.lect_name.setText(lecture.getTitle());

//        holder.progress.set

        float lectureProgress = prefManager.getLectureProgress("lecture" + lecture.getId());

        if(lectureProgress == -1){
            holder.progress.setValue(0.0f);
        }else {
            holder.progress.setValue(lectureProgress);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent lectureView = new Intent(context, LectureViewActivity.class);
            lectureView.putExtra("lect_id", lecture.getId());
            lectureView.putExtra("name", lecture.getTitle());
            lectureView.putExtra("url", lecture.getPdf());
            context.startActivity(lectureView);
        });


    }


    @Override
    public int getItemCount() {
        return lectures.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView lect_name;
        public Slider progress;

        public ViewHolder(View itemView) {
            super(itemView);
            this.lect_name = itemView.findViewById(R.id.name);
            this.progress = itemView.findViewById(R.id.progress);
        }
    }


}