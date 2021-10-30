package com.example.acckeep.customlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;


import com.example.acckeep.R;
import com.example.acckeep.functionality.Add_new;
//import com.example.acckeep.functionality.Edit_Program;
//import com.example.acckeep.functionality.Search;
import com.example.acckeep.objects.Credentials;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Credentials> allObjects = new ArrayList<>();
    private ArrayList<Credentials> searchCred = new ArrayList<>();
    private Credentials object;
    private FileInputStream fileIn;
    private ObjectInputStream in;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton addBttn = (FloatingActionButton) findViewById(R.id.addBttn);
        addBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddActivity();
            }
        });
        FloatingActionButton searchBttn = (FloatingActionButton) findViewById(R.id.searchPageBttn);
        searchBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openSearchActivity();
            }
        });
    }

    public void onStart() {
        createList();
        super.onStart();
    }

    public void onResume() {
        createList();
        super.onResume();
    }

    public void createList() {
        allObjects = load();
        listView = (ListView)findViewById(R.id.ListView);

        MyAdapter adapter = new MyAdapter(this, R.layout.row, allObjects);
        listView.setAdapter(adapter);
    }

    public void openAddActivity() {
        Intent intent = new Intent(this, Add_new.class);
        startActivity(intent);
    }

//    public void openEditActivity(Credentials edit) {
//        Intent intent = new Intent(this, Edit_Program.class);
//        intent.putExtra("Object", edit);
//        startActivity(intent);
//    }

//    public void openSearchActivity() {
//        Intent intent = new Intent(this, Search.class);
//        startActivity(intent);
//    }

    private ArrayList<Credentials> load(){
        allObjects.clear();
        boolean loop = true;
        Context context = this;
        try{
            fileIn =  context.openFileInput("saved.ser");
            in = new ObjectInputStream(fileIn);
            while(loop) {
                object = (Credentials) in.readObject();
                if(object != null) {
                    allObjects.add(object);
                } else {
                    loop = false;
                }
            }
        } catch(IOException | ClassNotFoundException e){}
        return allObjects;
    }

}