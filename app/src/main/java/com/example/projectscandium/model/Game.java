package com.example.projectscandium.model;

/**
 * Game (Class)
 * Purpose: Represents a game that a user can play. Stores the game's number of players,
 * the total sores of the players, the achievements, and the time of its creation.
 */
public class Game {
    // private variables to store the number of players, the total scores,
    // time and achievements
    private int combinedScore;
    private int playerNum;
    private String time;
    private Achievements achievements;

    // Game constructor
    // Purpose: creates a new Game object
    // Return: void
    public Game(int playerNum, int combinedScore, String string) {
        this.combinedScore = combinedScore;
        this.playerNum = playerNum;
        this.time = string;
    }

    // getCombinedScore
    // Purpose: returns the combined score
    // Return: int
    public int getCombinedScore() {
        return combinedScore;
    }

    // setCombinedScore
    // Purpose: sets the combined score
    // Return: void
    public void setCombinedScore(int combinedScore) {
        this.combinedScore = combinedScore;
    }

    // getPlayerNum
    // Purpose: returns the number of players
    // Return: int
    public int getPlayerNum() {
        return playerNum;
    }

    // setPlayerNum
    // Purpose: sets the number of players
    // Return: void
    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    // restoreGame
    // Purpose: restores the game
    // Return: void
    public void restoreGame(int players, int scores) {
        this.playerNum = players;
        this.combinedScore = scores;
    }

    // getTime
    // Purpose: returns the time of the game creation
    // Return: String
    public String getTime() {
        return time;
    }

    // setTime
    // Purpose: sets the time of the game creation
    // Return: void
    public void setTime(String time) {
        this.time = time;
    }

    // getAchievements
    // Purpose: returns the achievements
    // Return: Achievements
    public Achievements getAchievements() {
        return achievements;
    }

    // setAchievements
    // Purpose: sets the achievements
    // Return: void
    public void setAchievements(Achievements achievements) {
        this.achievements = achievements;
    }
}