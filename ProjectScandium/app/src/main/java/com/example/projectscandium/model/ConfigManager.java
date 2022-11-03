package com.example.projectscandium.model;

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

    // TODO: Array list to store games
    // private ArrayList<Game> games = new ArrayList<>();

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
}
