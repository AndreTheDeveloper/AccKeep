package com.example.acckeep.functionality;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.acckeep.R;
import com.example.acckeep.objects.Account;
import com.example.acckeep.objects.Application;
import com.example.acckeep.objects.Website;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
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

}
