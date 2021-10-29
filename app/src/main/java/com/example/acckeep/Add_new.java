package com.example.acckeep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Add_new extends AppCompatActivity {
    private ArrayList<Credentials> allObjects = new ArrayList<>();
    private FileOutputStream fileOut;
    private ObjectOutputStream out;
    private FileInputStream fileIn;
    private ObjectInputStream in;
    private Credentials object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        FloatingActionButton backBttn = (FloatingActionButton) findViewById(R.id.backHomeButton);
        backBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void addCredentials(View v) {
        View application = findViewById(R.id.addApplication);
        TextView appView = (TextView) application;
        String app = appView.getText().toString();
        View username = findViewById(R.id.addUsername);
        TextView usrView = (TextView) username;
        String usr = usrView.getText().toString();
        View password = findViewById(R.id.addPassword);
        TextView passView = (TextView) password;
        String pass = passView.getText().toString();

        if(app.length() > 0 && usr.length() > 0 && pass.length() > 0) {
            allObjects = load();
            Credentials cred = new Credentials(app, usr, pass);
           if(validation(cred)) {
               allObjects.add(cred);
               save(allObjects);
               Toast.makeText(this, "Credentials added successfully", Toast.LENGTH_LONG).show();
           }
            finish();
        }
        else {
            Toast.makeText(this, "One or more fields are empty", Toast.LENGTH_LONG).show();
        }
    }
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

    private void save(ArrayList<Credentials> allObjects) {
        Context context = this;
        try{
            fileOut = context.openFileOutput("saved.ser", Context.MODE_PRIVATE);
            out = new ObjectOutputStream(fileOut);
            for(int i = 0; i < allObjects.size(); i++){
                this.out.writeObject(allObjects.get(i));
            }
        } catch(Exception e) {
            Toast.makeText(this, "Error saving", Toast.LENGTH_LONG).show();
        }
    }

    private Boolean validation(Credentials cred) {
        for(int i = 0; i < allObjects.size(); i++) {
            if(allObjects.get(i).getUsername().equals(cred.getUsername()) &&
                    allObjects.get(i).getWebsite().equals(cred.getWebsite()) &&
                    allObjects.get(i).getPassword().equals(cred.getPassword())) {
                Toast.makeText(this, "Account info already saved", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }
}