package com.manush.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageButton imageButton;
    RecyclerView recyclerView;
    ArrayList<Note> notes;
    NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton = findViewById(R.id.img_add);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewInput = inflater.inflate(R.layout.note_input, null, false);

                EditText edtTitle = viewInput.findViewById(R.id.edt_title);
                EditText edtDescription = viewInput.findViewById(R.id.edt_description);

                new AlertDialog.Builder(MainActivity.this)
                        .setView(viewInput)
                        .setTitle("Add Notes")
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String title = edtTitle.getText().toString();
                                String description = edtDescription.getText().toString();

                                Note note = new Note(title,description);

                                boolean isInserted = new NoteHandler(MainActivity.this).create(note);

                                if(isInserted){
                                    Toast.makeText(MainActivity.this,"Note saved",Toast.LENGTH_SHORT).show();
                                    lodesNotes();
                                }else{
                                    Toast.makeText(MainActivity.this,"Unable to Save",Toast.LENGTH_SHORT).show();

                                }
                                dialogInterface.cancel();
                            }
                        }).show();
            }
        });

        recyclerView = findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        lodesNotes();
    }

    public ArrayList<Note> readNotes(){
        ArrayList<Note> notes = new NoteHandler(this).readNotes();
        return notes;
    }

    public void lodesNotes(){

        notes = readNotes();

        noteAdapter = new NoteAdapter(notes,this);

        recyclerView.setAdapter(noteAdapter);
    }
}