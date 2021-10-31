package com.example.acckeep.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

public class Edit_Program extends AppCompatActivity {
    private ArrayList<Account> allObjects = new ArrayList<>();
    private Website website;
    private Application app;
    private Intent intent;
    Context context;
    public static final String MyPREFERENCES = "nightModePrefs";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        context = this;
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }
    public void onStart() {
        Load.load(allObjects,this,sharedPreferences);
        editView();
        super.onStart();
    }
    private void editView() {
        intent = getIntent();
        if(intent.getSerializableExtra("Object") instanceof Website) {
            TextView title = (TextView) findViewById(R.id.textView2);
            title.setText("Website");

            website = (Website) intent.getSerializableExtra("Object");
            setContentView(R.layout.edit);

            EditText editApp = (EditText) findViewById(R.id.editApplication);
            editApp.setText(website.getWebsite());

            EditText editUsr = (EditText) findViewById(R.id.editUsername);
            editUsr.setText(website.getUsername());

            EditText editPass = (EditText) findViewById(R.id.editPassword);
            editPass.setText(website.getPassword());
        } else if(intent.getSerializableExtra("Object") instanceof Application){
            TextView title = (TextView) findViewById(R.id.textView2);
            title.setText("Application");

            app = (Application) intent.getSerializableExtra("Object");
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
            for (int i = 0; i < allObjects.size(); i++) {
                if(allObjects.get(i) instanceof Website && intent.getSerializableExtra("Object") instanceof Website) {
                    Website current = (Website) allObjects.get(i);
                    website = (Website) intent.getSerializableExtra("Object");
                    if (current.getUsername().equals(website.getUsername()) &&
                            current.getWebsite().equals(website.getWebsite()) &&
                            current.getPassword().equals(website.getPassword())) {
                        website.setWebsite(updatedApp);
                        website.setUsername(updatedUsr);
                        website.setPassword(updatedPass);
                        allObjects.remove(i);
                        allObjects.add(i, website);
                    }
                } else if (allObjects.get(i) instanceof Application && intent.getSerializableExtra("Object") instanceof Application){
                    Application current = (Application) allObjects.get(i);
                    app = (Application) intent.getSerializableExtra("Object");
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
            Save.save(allObjects, this);
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "One or more fields are empty", Toast.LENGTH_LONG).show();
        }
    }

    public void delete(View v) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm delete")
                .setMessage("Do you really want to delete?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        for(int i = 0; i < allObjects.size(); i++){
                            if(allObjects.get(i) instanceof Website && intent.getSerializableExtra("Object") instanceof Website) {
                                Website current = (Website) allObjects.get(i);
                                Website website = (Website) intent.getSerializableExtra("Object");
                                if(current.getUsername().equals(website.getUsername()) &&
                                        current.getWebsite().equals(website.getWebsite()) &&
                                        current.getPassword().equals(website.getPassword())) {
                                    allObjects.remove(i);
                                    finish();
                                }
                            } else if(allObjects.get(i) instanceof Application && intent.getSerializableExtra("Object") instanceof Application) {
                                Application current = (Application) allObjects.get(i);
                                Application app = (Application) intent.getSerializableExtra("Object");
                                if(current.getUsername().equals(app.getUsername()) &&
                                        current.getApp().equals(app.getApp()) &&
                                        current.getPassword().equals(app.getPassword())) {
                                    allObjects.remove(i);
                                    finish();
                                }
                            }
                        }
                        Save.save(allObjects, context);
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

}
