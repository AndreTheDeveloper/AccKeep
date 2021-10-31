package com.example.acckeep.backend;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.acckeep.R;
import com.example.acckeep.objects.Account;
import com.example.acckeep.objects.Application;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Load {

    public static final String KEY_ISNIGHTMODE = "isNightMode";

    public static ArrayList<Account> load(ArrayList<Account> allObjects, Context context, SharedPreferences sharedPreferences){
        allObjects.clear();
        boolean loop = true;
        try{
            FileInputStream fileIn =  context.openFileInput("saved.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            while(loop) {
                Account object = (Account) in.readObject();
                if(object != null) {
                    if(sharedPreferences.getBoolean(KEY_ISNIGHTMODE, false)) {
                        if(object instanceof Application) {
                            object.setImage(R.drawable.appiconlight);
                        }
                        else {
                            object.setImage(R.drawable.websiteiconlight);
                        }
                    } else {
                        if(object instanceof Application) {
                            object.setImage(R.drawable.appicondark);
                        }
                        else {
                            object.setImage(R.drawable.websiteicondark);
                        }
                    }
                    allObjects.add(object);
                } else {
                    loop = false;
                }
            }
        } catch(IOException | ClassNotFoundException e){}
        return allObjects;
    }

}
