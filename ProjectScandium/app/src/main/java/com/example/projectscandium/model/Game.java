package com.example.projectscandium.model;

import com.example.projectscandium.model.Configs;

public class Game {
    private int combinedScore;
    private int playerNum;
    private String time;

    public Game(int playerNum, int combinedScore, String string) {
        this.combinedScore = combinedScore;
        this.playerNum = playerNum;
        this.time = string;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}