package com.example.projectscandium.model;

import com.example.projectscandium.model.Configs;

public class Game {
    private int combinedScore;
    private int playerNum;

    public Game(int playerNum, int combinedScore) {
        this.combinedScore = combinedScore;
        this.playerNum = playerNum;
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

    public void restoreGame(int players, int scores){
        this.playerNum = players;
        this.combinedScore = scores;
    }
}