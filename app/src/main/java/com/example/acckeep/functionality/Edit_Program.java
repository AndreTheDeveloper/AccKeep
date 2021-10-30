package com.example.acckeep.functionality;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.acckeep.customlist.MainActivity;
import com.example.acckeep.R;
import com.example.acckeep.objects.Application;
import com.example.acckeep.objects.Credentials;
import com.example.acckeep.objects.Website;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Edit_Program extends AppCompatActivity {

    private FileOutputStream fileOut;
    private ObjectOutputStream out;
    private FileInputStream fileIn;
    private ObjectInputStream in;
    private ArrayList<Credentials> allObjects = new ArrayList<>();
    private Website website;
    private Application app;
    private Credentials object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);;
    }
    public void onStart() {
        load();
        editView();
        super.onStart();
    }
    private void editView() {
        Intent i = getIntent();
        if(i.getSerializableExtra("Object") instanceof Website) {
            website = (Website) i.getSerializableExtra("Object");
            setContentView(R.layout.edit);
            EditText editApp = (EditText) findViewById(R.id.editApplication);
            editApp.setText(website.getWebsite());
            EditText editUsr = (EditText) findViewById(R.id.editUsername);
            editUsr.setText(website.getUsername());
            EditText editPass = (EditText) findViewById(R.id.editPassword);
            editPass.setText(website.getPassword());
        } else {
            app = (Application) i.getSerializableExtra("Object");
            setContentView(R.layout.edit);
            EditText editApp = (EditText) findViewById(R.id.editApplication);
            editApp.setText(app.getApp());
            EditText editUsr = (EditText) findViewById(R.id.editUsername);
            editUsr.setText(app.getUsername());
            EditText editPass = (EditText) findViewById(R.id.editPassword);
            editPass.setText(app.getPassword());
        }

        FloatingActionButton backBttn2 = (FloatingActionButton) findViewById(R.id.backHomeButtonEdit);
        backBttn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMain();
            }
        });
    }

    public void backToMain() {
        finish();
    }

    private void save(ArrayList<Credentials> allObjects) {
        Context context = this;
        try{
            fileOut = context.openFileOutput("saved.ser", Context.MODE_PRIVATE);
            out = new ObjectOutputStream(fileOut);
            for(int i = 0; i < allObjects.size(); i++){
                this.out.writeObject(allObjects.get(i));
            }
        } catch(Exception e) {
            Toast.makeText(this, "Error saving", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveEdit(View v) {
        View application = findViewById(R.id.editApplication);
        TextView appView = (TextView) application;
        String updatedApp = appView.getText().toString();
        View username = findViewById(R.id.editUsername);
        TextView usrView = (TextView) username;
        String updatedUsr = usrView.getText().toString();
        View password = findViewById(R.id.editPassword);
        TextView passView = (TextView) password;
        String updatedPass = passView.getText().toString();

        if(updatedApp.length() > 0 && updatedUsr.length() > 0 && updatedPass.length() > 0) {
            for (int i = 0; i < allObjects.size(); i ++) {
                if(allObjects.get(i) instanceof Website) {
                    Website current = (Website) allObjects.get(i);
                    if (current.getUsername().equals(website.getUsername()) &&
                            current.getWebsite().equals(website.getWebsite()) &&
                            current.getPassword().equals(website.getPassword())) {
                        website.setWebsite(updatedApp);
                        website.setUsername(updatedUsr);
                        website.setPassword(updatedPass);
                        allObjects.remove(i);
                        allObjects.add(i, website);
                    }
                } else if (allObjects.get(i) instanceof Application){
                    Application current = (Application) allObjects.get(i);
                    if (current.getUsername().equals(app.getUsername()) &&
                            current.getApp().equals(app.getApp()) &&
                            current.getPassword().equals(app.getPassword())) {
                        app.setApp(updatedApp);
                        app.setUsername(updatedUsr);
                        app.setPassword(updatedPass);
                        allObjects.remove(i);
                        allObjects.add(i, app);
                    }
                }
            }
            save(allObjects);
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "One or more fields are empty", Toast.LENGTH_LONG).show();
        }
    }

//    public void delete(View v) {
//        new AlertDialog.Builder(this)
//                .setTitle("Confirm delete")
//                .setMessage("Do you really want to delete?")
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        for(int i = 0; i < allObjects.size(); i++){
//                            if(allObjects.get(i).getUsername().equals(object.getUsername()) &&
//                                    allObjects.get(i).getWebsite().equals(object.getWebsite()) &&
//                                    allObjects.get(i).getPassword().equals(object.getPassword())) {
//                                allObjects.remove(i);
//                                finish();
//                            }
//                        }
//                        save(allObjects);
//                    }})
//                .setNegativeButton(android.R.string.no, null).show();
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
