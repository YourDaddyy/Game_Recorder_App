package com.example.projectscandium;

import android.annotation.SuppressLint;
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

/*Class ConfigListAdapter
 * Purpose: This class is the adapter for the list of configs
 */
public class ConfigListAdapter extends ArrayAdapter<Configs> {

    // private variables to store the necessary information
    private final Context mContext;
    private final int mResource;

    // ConfigListAdapter constructor
    // Purpose: creates the adapter
    // Returns: none
    public ConfigListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Configs> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    // getView method
    // Purpose: creates the view for the list of configs for the list view
    // Returns: the view
    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView configName = convertView.findViewById(R.id.config_name);
        TextView greatExpectedScore = convertView.findViewById(R.id.upper_value);
        TextView poorExpectedScore = convertView.findViewById(R.id.lower_value);

        configName.setText(String.format("Config:  %s", getItem(position).getGameConfigName()));
        greatExpectedScore.setText(String.format("Great: %s", getItem(position).getGreatExpectedScore()));
        poorExpectedScore.setText(String.format("Poor: %s", getItem(position).getPoorExpectedScore()));

        return convertView;
    }
}
