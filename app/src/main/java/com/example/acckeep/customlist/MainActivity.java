package com.example.acckeep.customlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;

import android.widget.ImageView;
import android.widget.ListView;


import com.example.acckeep.R;
import com.example.acckeep.functionality.Add_new;
//import com.example.acckeep.functionality.Edit_Program;
//import com.example.acckeep.functionality.Search;
import com.example.acckeep.functionality.Edit_Program;
import com.example.acckeep.functionality.Search;
import com.example.acckeep.functionality.Settings;
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
    private Account object;
    private FileInputStream fileIn;
    private ObjectInputStream in;
    private ListView listView;
    private ImageView editImg;
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
        allObjects = load();
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

    private ArrayList<Account> load(){
        allObjects.clear();
        boolean loop = true;
        Context context = this;
        try{
            fileIn =  context.openFileInput("saved.ser");
            in = new ObjectInputStream(fileIn);
            while(loop) {
                object = (Account) in.readObject();
                if(object != null) {
                    if(sharedPreferences.getBoolean(KEY_ISNIGHTMODE, false)) {
                        if(object instanceof Application) {
                            object.setImage(R.drawable.appiconlight);
                        }
                        else {
                            object.setImage(R.drawable.websiteiconlight);
                        }
                    } else {
                        if(object instanceof Application) {
                            object.setImage(R.drawable.appicondark);
                        }
                        else {
                            object.setImage(R.drawable.websiteicondark);
                        }
                    }
                    allObjects.add(object);
                } else {
                    loop = false;
                }
            }
        } catch(IOException | ClassNotFoundException e){}
        return allObjects;
    }

}