package com.example.acckeep;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class CreatePassword extends AppCompatActivity {

    private String password = "";
    private FileOutputStream fileOut;
    private ObjectOutputStream out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_password);

    }

    public void selectButton(View v) {
        Button selected = (Button) v;
        password += ((Button) v).getText().toString();
        setText();
    }

    public void savePassword(View v) {
        if(password.length() > 0) {
            Context context = this;
            try {
                fileOut = context.openFileOutput("savedPassword.ser", Context.MODE_PRIVATE);
                out = new ObjectOutputStream(fileOut);
                out.writeObject(password);
            } catch (Exception e) {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            }
            finish();
        } else {
            Toast.makeText(this, "Password field is empty", Toast.LENGTH_LONG).show();
        }
    }

    public void backSpace(View v) {
        if(password.length() > 0) {
            password = password.substring(0, password.length() - 1);
            setText();
        } else {
            Toast.makeText(this, "Password field is empty", Toast.LENGTH_LONG).show();
        }
    }

    public void setText() {
        TextView viewPassword = (TextView) findViewById(R.id.createdPassword);
        viewPassword.setText(password);
    }
}
