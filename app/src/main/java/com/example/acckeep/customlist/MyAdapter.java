package com.example.acckeep.customlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acckeep.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.acckeep.objects.Credentials;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<Credentials> {
    private Context mContext;
    private int mResource;

    public MyAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Credentials> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);
        ImageView imageView = convertView.findViewById(R.id.icon);
        TextView desc = convertView.findViewById(R.id.info);
        imageView.setImageResource(getItem(position).getImage());
        desc.setText(getItem(position).toString());

        return convertView;
    }
}
