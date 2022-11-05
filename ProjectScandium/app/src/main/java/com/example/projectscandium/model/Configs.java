package com.example.projectscandium.model;

public class Configs {

    // variable to store expected game scores
    private int greatExpectedScore;
    private int poorExpectedScore;
    // variable to store name of game configuration
    private String gameConfigName;

    // game list of config
    private Game[] games;
    private int gameNum;
    private int MAX_LEN = 999;

    public Configs() {
        this.greatExpectedScore = 0;
        this.poorExpectedScore = 0;
        this.gameConfigName = "";
        this.games = new Game[MAX_LEN];
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

    public void addGame(Game game){
        this.games[this.gameNum] = game;
        this.gameNum ++;
    }

    public void deleteGame(int index){
        if(index == -1){return;}
        int dest = index;
        for(int i = index + 1; i < this.gameNum; i++){
            games[dest] = games[i];
            dest ++;
        }
        this.gameNum --;
    }

    public int calculateLevel(int players, int scores){
        return 0;
    }

    public Game searchGame(int index){
        return this.games[index];
    }

    public Game[] getGames() {
        return games;
    }

    public void setGames(Game[] games) {
        this.games = games;
    }

    public int getGameNum() {
        return gameNum;
    }

    public void setGameNum(int gameNum) {
        this.gameNum = gameNum;
    }
}
