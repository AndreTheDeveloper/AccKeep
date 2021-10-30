package com.example.acckeep.functionality;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.acckeep.customlist.MainActivity;
import com.example.acckeep.R;
import com.example.acckeep.customlist.MyAdapter;
import com.example.acckeep.objects.Account;
import com.example.acckeep.objects.Application;
import com.example.acckeep.objects.Website;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;


public class Search extends AppCompatActivity {

    private ArrayList<Account> allObjects = new ArrayList<>();
    private ArrayList<Account> search = new ArrayList<>();
    private Account object;
    private ListView searchResult;
    private FileInputStream fileIn;
    private ObjectInputStream in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_parameters);
        FloatingActionButton backBttn3 = (FloatingActionButton) findViewById(R.id.backButtonSearch);
        backBttn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void onStart() {
        EditText searchApplication = (EditText) findViewById(R.id.searchApplication);
        EditText searchUsername = (EditText) findViewById(R.id.searchUsername);
        EditText searchPassword = (EditText) findViewById(R.id.searchPassword);

        Button searchBttn = (Button)findViewById(R.id.searchBttn);
        if(searchBttn == null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else {
            searchBttn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    searchResults(searchApplication, searchUsername, searchPassword);
                }
            });
        }
        super.onStart();
    }
    public void onResume() {
        Button searchBttn = (Button)findViewById(R.id.searchBttn);
        if(searchBttn == null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        super.onResume();
    }
    public void searchResults(EditText searchApp, EditText searchUsr, EditText searchPass) {

        if(searchApp.getText().toString().length() > 0 ||
                searchUsr.getText().toString().length() > 0 ||
                searchPass.getText().toString().length() > 0) {
                setContentView(R.layout.search_results);
                FloatingActionButton backBttn3 = (FloatingActionButton) findViewById(R.id.backButtonSearchResults);
                backBttn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
                allObjects = load();
                for(int i = 0; i < allObjects.size(); i++) {
                    if (allObjects.get(i) instanceof Website) {
                        Website current = (Website) allObjects.get(i);
                        if (current.getWebsite().toLowerCase().contains(searchApp.getText().toString().toLowerCase()) &&
                                current.getUsername().toLowerCase().contains(searchUsr.getText().toString().toLowerCase()) &&
                                current.getPassword().toLowerCase().contains(searchPass.getText().toString().toLowerCase())) {
                            search.add(allObjects.get(i));
                        }
                    } else if(allObjects.get(i) instanceof Application) {
                        Application current = (Application) allObjects.get(i);
                        if (current.getApp().toLowerCase().contains(searchApp.getText().toString().toLowerCase()) &&
                                current.getUsername().toLowerCase().contains(searchUsr.getText().toString().toLowerCase()) &&
                                current.getPassword().toLowerCase().contains(searchPass.getText().toString().toLowerCase())) {
                            search.add(allObjects.get(i));
                        }
                    }
                }
                searchResult = (ListView)findViewById(R.id.searchList);
                MyAdapter adapter = new MyAdapter(this, R.layout.row, search);
                searchResult.setAdapter(adapter);

                searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if(searchResult.getItemAtPosition(position) instanceof Website) {
                            Website edit = (Website) searchResult.getItemAtPosition(position);
                            openEditActivity(edit);
                        }
                        else if (searchResult.getItemAtPosition(position) instanceof Application){
                            Application edit = (Application) searchResult.getItemAtPosition(position);
                            openEditActivity(edit);
                        }
                    }
                });


        } else {
            Toast.makeText(this, "One or more search fields are empty", Toast.LENGTH_LONG).show();
        }
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
                    allObjects.add(object);
                } else {
                    loop = false;
                }
            }
        } catch(IOException | ClassNotFoundException e){}
        return allObjects;
    }

    public void openEditActivity(Account edit) {
        Intent intent = new Intent(this, Edit_Program.class);
        intent.putExtra("Object", edit);
        startActivity(intent);
    }
}
