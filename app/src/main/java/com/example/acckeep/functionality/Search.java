//package com.example.acckeep.functionality;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.Toast;
//
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.acckeep.customlist.MainActivity;
//import com.example.acckeep.R;
//import com.example.acckeep.objects.Credentials;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.util.ArrayList;
//
//
//public class Search extends AppCompatActivity {
//
//    private ArrayList<Credentials> allObjects = new ArrayList<>();
//    private ArrayList<Credentials> searchCred = new ArrayList<>();
//    private Credentials object;
//    private ListView searchResult;
//    private FileInputStream fileIn;
//    private ObjectInputStream in;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.search_parameters);
//        FloatingActionButton backBttn3 = (FloatingActionButton) findViewById(R.id.backButtonSearch);
//        backBttn3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//    }
//
//    public void onStart() {
//        EditText searchApplication = (EditText) findViewById(R.id.searchApplication);
//        EditText searchUsername = (EditText) findViewById(R.id.searchUsername);
//        EditText searchPassword = (EditText) findViewById(R.id.searchPassword);
//
//        Button searchBttn = (Button)findViewById(R.id.searchBttn);
//        if(searchBttn == null) {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//        }else {
//            searchBttn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    searchResults(searchApplication, searchUsername, searchPassword);
//                }
//            });
//        }
//        super.onStart();
//    }
//    public void onResume() {
//        Button searchBttn = (Button)findViewById(R.id.searchBttn);
//        if(searchBttn == null) {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//        }
//        super.onResume();
//    }
//    public void searchResults(EditText searchApp, EditText searchUsr, EditText searchPass) {
//
//        if(searchApp.getText().toString().length() > 0 ||
//                searchUsr.getText().toString().length() > 0 ||
//                searchPass.getText().toString().length() > 0) {
//                setContentView(R.layout.search_results);
//                FloatingActionButton backBttn3 = (FloatingActionButton) findViewById(R.id.backButtonSearchResults);
//                backBttn3.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        finish();
//                    }
//                });
//                allObjects = load();
//                for(int i = 0; i < allObjects.size(); i++) {
//                    if(allObjects.get(i).getWebsite().toLowerCase().contains(searchApp.getText().toString().toLowerCase()) &&
//                            allObjects.get(i).getUsername().toLowerCase().contains(searchUsr.getText().toString().toLowerCase()) &&
//                            allObjects.get(i).getPassword().toLowerCase().contains(searchPass.getText().toString().toLowerCase())) {
//                                searchCred.add(allObjects.get(i));
//                    }
//                }
//                searchResult = (ListView)findViewById(R.id.searchList);
//                ArrayAdapter<Credentials> adapter2 = new ArrayAdapter<Credentials>(this, android.R.layout.simple_list_item_1,searchCred);
//                searchResult.setAdapter(adapter2);
//                searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener()
//                {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View v, int position, long l) {
//                        object = searchCred.get(position);
//                        openEditActivity(object);
//                    }
//                });
//        } else {
//            Toast.makeText(this, "One or more search fields are empty", Toast.LENGTH_LONG).show();
//        }
//    }
//    private ArrayList<Credentials> load(){
//        allObjects.clear();
//        boolean loop = true;
//        Context context = this;
//        try{
//            fileIn =  context.openFileInput("saved.ser");
//            in = new ObjectInputStream(fileIn);
//            while(loop) {
//                object = (Credentials) in.readObject();
//                if(object != null) {
//                    allObjects.add(object);
//                } else {
//                    loop = false;
//                }
//            }
//        } catch(IOException | ClassNotFoundException e){}
//        return allObjects;
//    }
//
//    public void openEditActivity(Credentials edit) {
//        Intent intent = new Intent(this, Edit_Program.class);
//        intent.putExtra("Object", edit);
//        startActivity(intent);
//    }
//}
