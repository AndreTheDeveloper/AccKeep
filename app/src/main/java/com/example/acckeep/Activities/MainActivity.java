package com.example.acckeep.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;

import android.widget.ImageView;
import android.widget.ListView;


import com.example.acckeep.R;
import com.example.acckeep.backend.Load;
import com.example.acckeep.customlist.MyAdapter;
//import com.example.acckeep.functionality.Edit_Program;
//import com.example.acckeep.functionality.Search;
import com.example.acckeep.objects.Application;
import com.example.acckeep.objects.Account;
import com.example.acckeep.objects.Website;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Account> allObjects = new ArrayList<>();
    private ArrayList<Account> searchCred = new ArrayList<>();
    private ListView listView;
    public static final String MyPREFERENCES = "nightModePrefs";
    public static final String KEY_ISNIGHTMODE = "isNightMode";
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
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
                openSearchActivity();
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
        allObjects = Load.load(allObjects, this, sharedPreferences);
        listView = findViewById(R.id.ListView);

        MyAdapter adapter = new MyAdapter(this, R.layout.row, allObjects, sharedPreferences);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listView.getItemAtPosition(position) instanceof Website) {
                    Website edit = (Website) listView.getItemAtPosition(position);
                    openEditActivity(edit);
                }
                else if (listView.getItemAtPosition(position) instanceof Application){
                    Application edit = (Application) listView.getItemAtPosition(position);
                    openEditActivity(edit);
                }
            }
        });
    }

    public void openAddActivity() {
        Intent intent = new Intent(this, Add_new.class);
        startActivity(intent);
    }


    public void openEditActivity(Account edit) {
        Intent intent = new Intent(this, Edit_Program.class);
        intent.putExtra("Object", edit);
        startActivity(intent);
    }

    public void openSearchActivity() {
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    public void openSettings(View v) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

}