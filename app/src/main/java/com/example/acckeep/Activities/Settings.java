package com.example.acckeep.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.acckeep.R;

public class Settings extends AppCompatActivity {

    private Switch aSwitch;
    public static final String MyPREFERENCES = "nightModePrefs";
    public static final String KEY_ISNIGHTMODE = "isNightMode";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        aSwitch = findViewById(R.id.themeSwitch);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        checkNightModeActivated();

        aSwitch.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if(isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                saveNightModeState(true);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                saveNightModeState(false);
            }
            recreate();
        }
               ));
    }

    private void saveNightModeState(boolean nightMode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(KEY_ISNIGHTMODE, nightMode);

        editor.apply();
    }

    public void checkNightModeActivated() {
        if(sharedPreferences.getBoolean(KEY_ISNIGHTMODE, false)) {
            aSwitch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            aSwitch.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public void changePassword(View v) {
        Intent intent = new Intent(this, CreatePassword.class);
        startActivity(intent);
    }

    public void deleteEverything(View v) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm deletion of everything")
                .setMessage("Do you really want to delete all saved accounts?There is no recovery upon deletion")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        deleteFile("saved.ser");
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    public void backToMain(View v) {
        finish();
    }

}
