package com.example.projectscandium.model;

import java.util.ArrayList;

public class Configs {

    // variable to store expected game scores
    private int greatExpectedScore;
    private int poorExpectedScore;
    // variable to store name of game configuration
    private String gameConfigName;

    // game list of config
    private ArrayList<Game> games;
    private int gameNum;

    public Configs() {
        this.greatExpectedScore = 0;
        this.poorExpectedScore = 0;
        this.gameConfigName = "";
        this.games = new ArrayList<>();
        this.gameNum = 0;
    }

    public int getGreatExpectedScore() {
        return greatExpectedScore;
    }

    public void setGreatExpectedScore(int greatExpectedScore) {
        this.greatExpectedScore = greatExpectedScore;
    }

    public int getPoorExpectedScore() {
        return poorExpectedScore;
    }

    public void setPoorExpectedScore(int poorExpectedScore) {
        this.poorExpectedScore = poorExpectedScore;
    }

    public String getGameConfigName() {
        return gameConfigName;
    }

    public void setGameConfigName(String gameConfigName) {
        this.gameConfigName = gameConfigName;
    }

    public void addGame(Game game) {
        this.games.add(game);
        this.gameNum++;
    }

    public void deleteGame(int index) {
        if (index == -1) {
            return;
        }
        games.remove(index);
        this.gameNum--;
    }

    public Game searchGame(int index) {
        return this.games.get(index);
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    public int getGameNum() {
        return gameNum;
    }

    public void setGameNum(int gameNum) {
        this.gameNum = gameNum;
    }
}
