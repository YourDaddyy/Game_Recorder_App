package com.example.projectscandium.model;

public class Configs {

    // variable to store expected game scores
    private int greatExpectedScore;
    private int poorExpectedScore;
    // variable to store name of game configuration
    private String gameConfigName;

    public Configs() {
        this.greatExpectedScore = 0;
        this.poorExpectedScore = 0;
        this.gameConfigName = "";
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
}
