package com.example.acckeep.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acckeep.R;
import com.example.acckeep.backend.Load;
import com.example.acckeep.backend.Save;
import com.example.acckeep.objects.Application;
import com.example.acckeep.objects.Account;
import com.example.acckeep.objects.Website;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Add_new extends AppCompatActivity {
    private ArrayList<Account> allObjects = new ArrayList<>();
    public static final String MyPREFERENCES = "nightModePrefs";

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
            allObjects = Load.load(allObjects, this, getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE));

            Switch sw = (Switch) findViewById(R.id.switch1);
            if(sw.isChecked()) {
                Application ap = new Application(app,R.drawable.appicondark,usr,pass);
                if(appValidation(ap)) {
                    allObjects.add(ap);
                    Save.save(allObjects, this);
                    Toast.makeText(this, "Account added successfully", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Website web = new Website(app,R.drawable.websiteicondark,usr,pass);
                if(webValidation(web)) {
                    allObjects.add(web);
                    Save.save(allObjects, this);
                    Toast.makeText(this, "Account added successfully", Toast.LENGTH_SHORT).show();
                }
            }

            finish();
        }
        else {
            Toast.makeText(this, "One or more fields are empty", Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean webValidation(Website web) {
        for(int i = 0; i < allObjects.size(); i++) {
            if(allObjects.get(i) instanceof Website) {
                Website current = (Website) allObjects.get(i);
                if(current.getUsername().equalsIgnoreCase(web.getUsername()) &&
                        current.getWebsite().equalsIgnoreCase(web.getWebsite()) &&
                        current.getPassword().equalsIgnoreCase(web.getPassword())) {
                    Toast.makeText(this, "That Account Is Already Stored", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        return true;
    }

    private Boolean appValidation(Application app) {
        for(int i = 0; i < allObjects.size(); i++) {
            if(allObjects.get(i) instanceof Application) {
                Application current = (Application) allObjects.get(i);
                if(current.getUsername().equalsIgnoreCase(app.getUsername()) &&
                        current.getApp().equalsIgnoreCase(app.getApp()) &&
                        current.getPassword().equalsIgnoreCase(app.getPassword())) {
                    Toast.makeText(this, "That Account Is Already Stored", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }

        }
        return true;
    }
}