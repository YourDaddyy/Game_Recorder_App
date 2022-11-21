package com.example.projectscandium.model;

import java.util.ArrayList;

/**
 * Configs (Class)
 * Purpose: Represents the possible achievements that a game configuration can
 * have.
 * Stores the games, achievements, and the game configuration (i.e the name and
 * expected scores)
 */
public class Configs {

    // private variables to store the name of the config, the games, and the
    // achievements
    private int greatExpectedScore;
    private int poorExpectedScore;
    private String gameConfigName;

    // private array list to store the games and integer to store the number of
    // games in the config
    private ArrayList<Game> games;
    private int gameNum;

    // Configs constructor
    // Purpose: creates a new Configs object
    // Return: void
    public Configs() {
        this.greatExpectedScore = 0;
        this.poorExpectedScore = 0;
        this.gameConfigName = "";
        this.games = new ArrayList<>();
        this.gameNum = 0;
    }

    // getGreatExpectedScore
    // Purpose: returns the great expected score
    // Return: int
    public int getGreatExpectedScore() {
        return greatExpectedScore;
    }

    // setGreatExpectedScore
    // Purpose: sets the great expected score
    // Return: void
    public void setGreatExpectedScore(int greatExpectedScore) {
        this.greatExpectedScore = greatExpectedScore;
    }

    // getPoorExpectedScore
    // Purpose: returns the poor expected score
    // Return: int
    public int getPoorExpectedScore() {
        return poorExpectedScore;
    }

    // setPoorExpectedScore
    // Purpose: sets the poor expected score
    // Return: void
    public void setPoorExpectedScore(int poorExpectedScore) {
        this.poorExpectedScore = poorExpectedScore;
    }

    // getGameConfigName
    // Purpose: returns the game config name
    // Return: String
    public String getGameConfigName() {
        return gameConfigName;
    }

    // setGameConfigName
    // Purpose: sets the game config name
    // Return: void
    public void setGameConfigName(String gameConfigName) {
        this.gameConfigName = gameConfigName;
    }

    // addGame
    // Purpose: adds a new game to the games array list
    // Return: void
    public void addGame(Game game) {
        this.games.add(game);
        this.gameNum++;
    }

    // deleteGame
    // Purpose: deletes the game at the given index
    // Return: void
    public void deleteGame(int index) {
        if (index == -1) {
            return;
        }
        games.remove(index);
        this.gameNum--;
    }

    // searchGame
    // Purpose: searches for a game with the given name
    // Return: int
    public Game searchGame(int index) {
        return this.games.get(index);
    }

    // getGames
    // Purpose: returns the games array list
    // Return: ArrayList<Game>
    public ArrayList<Game> getGames() {
        return games;
    }

    // setGames
    // Purpose: sets the games array list
    // Return: void
    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    // getGameNum
    // Purpose: returns the number of games in the config
    // Return: int
    public int getGameNum() {
        return gameNum;
    }

    // setGameNum
    // Purpose: sets the number of games in the config
    // Return: void
    public void setGameNum(int gameNum) {
        this.gameNum = gameNum;
    }
}
