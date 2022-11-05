package com.example.projectscandium.model;

import com.example.projectscandium.model.Configs;

public class Game {
    private int combinedScore;
    private int playerNum;
    private int level;

    public Game(int combinedScore, int playerNum, int level) {
        this.combinedScore = combinedScore;
        this.playerNum = playerNum;
        this.level = level;
    }

    public int getCombinedScore() {
        return combinedScore;
    }

    public void setCombinedScore(int combinedScore) {
        this.combinedScore = combinedScore;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}