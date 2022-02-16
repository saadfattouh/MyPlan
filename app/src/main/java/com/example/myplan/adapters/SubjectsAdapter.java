package com.example.myplan.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myplan.MainActivity;
import com.example.myplan.R;
import com.example.myplan.SubjectActivity;
import com.example.myplan.model.Subject;

import java.util.ArrayList;
import java.util.List;

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.ViewHolder> {


    Context context;
    private List<Subject> depts;

    // RecyclerView recyclerView;
    public SubjectsAdapter(Context context, ArrayList<Subject> depts) {
        this.context = context;
        this.depts = depts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.subject_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Subject dept = depts.get(position);

        holder.dept.setText(dept.getSubjectName());

        holder.dept.setOnClickListener(v -> {
            context.startActivity(new Intent(context, SubjectActivity.class));
        });


    }


    @Override
    public int getItemCount() {
        return depts.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public Button dept;

        public ViewHolder(View itemView) {
            super(itemView);
            this.dept = itemView.findViewById(R.id.dept);
        }
    }


}