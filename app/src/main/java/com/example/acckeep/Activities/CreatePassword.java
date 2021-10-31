package com.example.acckeep.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.acckeep.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class CreatePassword extends AppCompatActivity {

    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_password);
    }

    public void selectButton(View v) {
        password += ((Button) v).getText().toString();
        setText();
    }

    public void savePassword(View v) {
        if(password.length() >= 4) {
            try {
                File file = new File(this.getFilesDir(), "password.ser");
                FileOutputStream fos = openFileOutput("password.ser", Context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(password);
                finish();
            } catch (Exception e) {}
        }
        else if(password.length() < 4 && password.length() > 0) {
            Toast.makeText(this, "Password is too short", Toast.LENGTH_SHORT).show();
        }
        else if(password.length() < 1) {
            Toast.makeText(this, "Password field is empty", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void backSpace(View v) {
        if(password.length() > 0) {
            password = password.substring(0, password.length() - 1);
            setText();
        } else {
            Toast.makeText(this, "Password field is empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void setText() {
        TextView viewPassword = (TextView) findViewById(R.id.createdPass);
        viewPassword.setText(password);
    }
}
