package com.example.acckeep.backend;

import android.content.Context;

import com.example.acckeep.objects.Account;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Save {

    public static void save(ArrayList<Account> allObjects, Context context) {
        try{
            FileOutputStream fileOut = context.openFileOutput("saved.ser", Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for(int i = 0; i < allObjects.size(); i++){
                out.writeObject(allObjects.get(i));
            }
        } catch(Exception e) {
        }
    }
}
