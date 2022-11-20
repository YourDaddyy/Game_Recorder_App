package com.example.projectscandium.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * ConfigManager (Singleton Class)
 * Purpose: Represents the possible achievements that a game configuration can
 * have. Stores the
 * achievements in a shared preferences file.
 */
public class ConfigManager {
    private static ConfigManager instance = null;

    // ConfigManager constructor
    // Purpose: creates a new ConfigManager object
    // Return: void
    private ConfigManager() {
    }

    // getInstance
    // Purpose: returns the instance of the ConfigManager object
    // Return: ConfigManager
    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    // Private Array list variable to store configs
    private ArrayList<Configs> configs = new ArrayList<>();

    // addConfig
    // Purpose: adds a new config to the configs array list
    // Return: void
    public void addConfig(Configs config) {
        configs.add(config);
    }

    // getConfig
    // Purpose: returns the config at the given index
    // Return: Configs
    public Configs getConfigById(int id) {
        if (id < 0 || id >= configs.size()) {
            throw new IndexOutOfBoundsException();
        } else {
            return configs.get(id);
        }
    }

    // removeConfig
    // Purpose: removes the config at the given index
    // Return: void
    public void removeConfigById(int id) {
        if (id < 0 || id >= configs.size()) {
            throw new IndexOutOfBoundsException();
        } else {
            configs.remove(id);
        }
    }

    // getSize
    // Purpose: returns the size of the configs array list
    // Return: int
    public int getSize() {
        return configs.size();
    }

    // getConfigs
    // Purpose: returns the configs array list
    // Return: ArrayList<Configs>
    public ArrayList<Configs> getConfigs() {
        return configs;
    }

    // saveConfigs
    // Purpose: saves the configs array list to a shared preferences file
    // Return: void
    public void saveConfigs(Context mContext) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences("Configs", Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(configs);
        editor.putString("config list", json);
        editor.apply();
    }

    // loadConfigs
    // Purpose: loads the configs array list from a shared preferences file and
    // populates
    // the configs instance
    // Return: void
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
