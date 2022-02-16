package com.example.myplan.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myplan.R;
import com.example.myplan.sqlite.Myappdatabas;
import com.example.myplan.sqlite.Notes;

import java.util.ArrayList;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {


    ArrayList<Notes> notes;
    Context context;

    Myappdatabas myappdatabas;

    public NotesAdapter(ArrayList<Notes> notes, Context context) {
        this.notes = notes;
        this.context = context;
        myappdatabas = Myappdatabas.getDatabase(context);
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_task, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Notes note = notes.get(position);

        holder.checkBox.setChecked(note.isDone());

        holder.checkBox.setText(note.getNote());

        if(note.isDone()){
            holder.checkBox.setPaintFlags(holder.checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else {
            holder.checkBox.setPaintFlags(holder.checkBox.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                note.setDone(isChecked);
                updateNote(position, note);
                holder.checkBox.setPaintFlags(holder.checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }else {
                note.setDone(isChecked);
                updateNote(position, note);
                holder.checkBox.setPaintFlags(holder.checkBox.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            }
        });

        holder.itemView.setOnLongClickListener(v -> {

                LayoutInflater factory = LayoutInflater.from(context);
                final View view = factory.inflate(R.layout.delete_confirmation_dialog, null);
                final AlertDialog deleteNoteDialog = new AlertDialog.Builder(context).create();
                deleteNoteDialog.setView(view);

                TextView yes = view.findViewById(R.id.yes_btn);
                TextView no = view.findViewById(R.id.no_btn);


                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //to see changes immediately
                        removeNote(position);
                        //when done dismiss;
                        deleteNoteDialog.dismiss();

                    }
                });

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteNoteDialog.dismiss();
                    }
                });
                deleteNoteDialog.show();

                return true;
            });
    }


    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            this.checkBox = itemView.findViewById(R.id.checkbox);
        }
    }

    private void removeNote(int index) {
        myappdatabas.myDao().deleteNote(notes.get(index));
        notes.remove(index);
        notifyItemRemoved(index);
    }


    private void updateNote(int index, Notes note) {
        myappdatabas.myDao().updateNote(note);
        notes.set(index, note);
    }



}
