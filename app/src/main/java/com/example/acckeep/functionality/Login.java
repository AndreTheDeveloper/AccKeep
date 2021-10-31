package com.example.acckeep.functionality;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.acckeep.customlist.MainActivity;
import com.example.acckeep.R;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


public class Login extends AppCompatActivity {
    private FileInputStream fileIn;
    private ObjectInputStream in;
    private String password;
    private String guess = "";
    private TextView guessedPass;
    public static final String MyPREFERENCES = "nightModePrefs";
    public static final String KEY_ISNIGHTMODE = "isNightMode";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        checkNightModeActivated();
    }

    public void onStart() {
        password = loadPassword();
        if(password == null) {
            Intent intent = new Intent(this, CreatePassword.class);
            startActivity(intent);
        }
        super.onStart();
    }

    public void checkNightModeActivated() {
        if(sharedPreferences.getBoolean(KEY_ISNIGHTMODE, false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public String loadPassword() {
        Context context = this;
        try {
            fileIn = context.openFileInput("password.ser");
            in = new ObjectInputStream(fileIn);
            password = (String) in.readObject();
        } catch(IOException | ClassNotFoundException e){}
        return password;
    }

    public void onResume() {
        guess = "";
        guessedPass = (TextView) findViewById(R.id.createdPass);
        guessedPass.setText("");
        super.onResume();
    }

    public void guessedNum(View v) {
        guess += ((Button) v).getText().toString();
        setText();
    }

    public void setText() {
        guessedPass = (TextView) findViewById(R.id.createdPass);
        guessedPass.setText(guess);
        validate(guess);
    }

    public void backSpace(View v) {
        if(guess.length() > 0) {
            guess = guess.substring(0, guess.length() - 1);
            setText();
        }
    }

    public void validate(String guess) {
        if(guess.equals(password)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

}
