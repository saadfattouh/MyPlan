package com.example.myplan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myplan.adapters.NotesAdapter;
import com.example.myplan.sqlite.Myappdatabas;
import com.example.myplan.sqlite.Notes;
import com.example.myplan.utils.SharedPrefManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    NotesAdapter mAdapter;
    RecyclerView mNotesList;
    ArrayList<Notes> notes;

    TextView mAddNoteBtn, mSeeAllBtn;


    Myappdatabas myappdatabas;

    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userId = SharedPrefManager.getInstance(this).getUserId();

        myappdatabas = Myappdatabas.getDatabase(this);

        mNotesList = findViewById(R.id.rv_tasks);
        mAddNoteBtn = findViewById(R.id.add_tasks);

        mSeeAllBtn = findViewById(R.id.see_all);

        mSeeAllBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, ChooseSubject.class));
        });


        loadNotes();


        mAddNoteBtn.setOnClickListener(v -> {
            LayoutInflater factory = LayoutInflater.from(this);
            final View view1 = factory.inflate(R.layout.dialog_add_task, null);
            final AlertDialog addNewNoteDialog = new AlertDialog.Builder(this).create();
            addNewNoteDialog.setView(view1);

            addNewNoteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            EditText mNote = view1.findViewById(R.id.note);
            TextView yes = view1.findViewById(R.id.yes_btn);
            TextView no = view1.findViewById(R.id.no_btn);


            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String text = mNote.getText().toString();
                    if(TextUtils.isEmpty(text)){
                        Toast.makeText(MainActivity.this, "note details must not be empty", Toast.LENGTH_SHORT).show();
                    }else {
                        Notes note = new Notes();
                        note.setDone(false);
                        note.setUserId(userId);
                        note.setNote(text);
                        myappdatabas.myDao().addNote(note);
                        loadNotes();
                    }

                    //when done dismiss;
                    addNewNoteDialog.dismiss();

                }
            });

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addNewNoteDialog.dismiss();
                }
            });
            addNewNoteDialog.show();
        });
    }

    private void loadNotes() {
        notes = (ArrayList<Notes>) myappdatabas.myDao().getUserNotes(userId);
        mAdapter = new NotesAdapter(notes, this);
        mNotesList.setAdapter(mAdapter);
    }
}