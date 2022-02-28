package com.example.myplan.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myplan.CoursesActivity;
import com.example.myplan.R;
import com.example.myplan.CourseActivity;
import com.example.myplan.model.Course;

import java.util.ArrayList;
import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.ViewHolder> {


    Context context;
    private List<Course> courses;

    // RecyclerView recyclerView;
    public CoursesAdapter(Context context, ArrayList<Course> courses) {
        this.context = context;
        this.courses = courses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_course, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Course course = courses.get(position);

        holder.course_name.setText(course.getCourseName());

        holder.course_name.setOnClickListener(v -> {
            Intent courseDetails = new Intent(context, CourseActivity.class);
            courseDetails.putExtra("course_id", course.getId());
            courseDetails.putExtra("name", course.getCourseName());
            context.startActivity(courseDetails);
        });


    }


    @Override
    public int getItemCount() {
        return courses.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public Button course_name;

        public ViewHolder(View itemView) {
            super(itemView);
            this.course_name = itemView.findViewById(R.id.dept);
        }
    }


}