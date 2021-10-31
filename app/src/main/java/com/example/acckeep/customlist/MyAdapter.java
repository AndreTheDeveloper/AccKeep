package com.example.acckeep.customlist;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acckeep.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.acckeep.objects.Account;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<Account> {
    private Context mContext;
    private int mResource;
    public static final String KEY_ISNIGHTMODE = "isNightMode";
    SharedPreferences sharedPreferences;

    public MyAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Account> objects, SharedPreferences sharedPreferences) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.sharedPreferences = sharedPreferences;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);
        ImageView imageView1 = convertView.findViewById(R.id.icon);
        ImageView imageView2 = convertView.findViewById(R.id.editBttn);
        TextView desc = convertView.findViewById(R.id.info);
        imageView1.setImageResource(getItem(position).getImage());
        if(sharedPreferences.getBoolean(KEY_ISNIGHTMODE, false)) {
            imageView2.setImageResource(R.drawable.settingsiconlight);
        } else {
            imageView2.setImageResource(R.drawable.settingsicondark);
        }
        desc.setText(getItem(position).toString());

        return convertView;
    }

}
