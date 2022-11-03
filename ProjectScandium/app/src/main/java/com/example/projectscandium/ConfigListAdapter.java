package com.example.projectscandium;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projectscandium.model.Configs;

import java.util.ArrayList;

public class ConfigListAdapter extends ArrayAdapter<Configs> {

    private final Context mContext;
    private final int mResource;

    public ConfigListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Configs> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView configName = convertView.findViewById(R.id.config_name);
        TextView greatExpectedScore = convertView.findViewById(R.id.upper_value);
        TextView poorExpectedScore = convertView.findViewById(R.id.lower_value);

        configName.setText(getItem(position).getGameConfigName());
        greatExpectedScore.setText(String.valueOf(getItem(position).getGreatExpectedScore()));
        poorExpectedScore.setText(String.valueOf(getItem(position).getPoorExpectedScore()));

        return convertView;
    }
}
