package com.example.projectscandium.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ConfigManager {
    private static ConfigManager instance = null;

    private ConfigManager() {
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    // Array list to store configs
    private ArrayList<Configs> configs = new ArrayList<>();

    // add a config to the array list
    public void addConfig(Configs config) {
        configs.add(config);
    }

    // get a config from the array list
    public Configs getConfigById(int id) {
        if (id < 0 || id >= configs.size()) {
            throw new IndexOutOfBoundsException();
        } else {
            return configs.get(id);
        }
    }

    // remove a config from the array list
    public void removeConfigById(int id) {
        if (id < 0 || id >= configs.size()) {
            throw new IndexOutOfBoundsException();
        } else {
            configs.remove(id);
        }
    }

    // function to get the size of the array list
    public int getSize() {
        return configs.size();
    }

    // function to return the array list
    public ArrayList<Configs> getConfigs() {
        return configs;
    }

    // function to set the array list values
    public void saveConfigs(Context mContext) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences("Configs", Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(configs);
        editor.putString("config list", json);
        editor.apply();
    }

    // function to get the array list values
    public void loadConfigs(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("Configs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("config list", null);
        Type type = new TypeToken<ArrayList<Configs>>() {
        }.getType();
        configs = gson.fromJson(json, type);
        if (configs == null) {
            configs = new ArrayList<>();
        }
    }

}
