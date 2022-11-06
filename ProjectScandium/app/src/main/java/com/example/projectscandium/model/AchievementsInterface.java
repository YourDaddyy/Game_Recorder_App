package com.example.projectscandium.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the 8 achievement levels based on number of players.
 */
public class AchievementsInterface {

    // A manager for the list of achievements.
    private static AchievementsManager manager;

    // The number of players for this game.
    private final int playerCount;

    // lowest expected lvl score
    private final int lower;

    // highest expected lvl score
    private final int high;
    // Enum of predetermined achievements for the game.
    AchievementsList[] achievements = AchievementsList.values();

    private final List<Achievements> achieved = new ArrayList<>();
    private final List<Achievements> unachieved = new ArrayList<>();

    public AchievementsInterface(int playerCount, int lowScore, int highScore) {

        manager = new AchievementsManager();
        this.playerCount = playerCount;
        this.lower = lowScore;
        this.high = highScore;
        createAchievement();
    }

    // Adjust the 8 levels based on number of players and creates achievement levels
    private void createAchievement() {

        // Add default worse score
//        manager.add(new Achievements("worse cat", 0));

        // Creates a distribution of levels between high and low
        double diff = (double)(high - lower) / 7;

        int count = 0;

        // Iterates through enum data and populates new achievement levels into manager
        for (AchievementsList lvl: achievements) {
            // Adjusts base score to number of players
            double adjustedScore = (lower + diff * count) * playerCount;
            manager.add(new Achievements(lvl.getLevelName(), adjustedScore));
            count++;
        }

    }
    
    // Marks whether achievement has been achieved or not
    public void checkAchievements(int score) {
        
        // Iterates through achievement levels from manager
        for (Achievements lvl: manager.getAchievements()) {

            // Checks whether score completes the achievement
            if (score >= lvl.getScore()) {
                lvl.setCompletion(true);
            }
        }
    }
    
    // Returns list of achievements that have been completed
    public List<Achievements> getCompletedAchievements(int score) {
        
        // Iterates through achievement levels from manager
        for (Achievements lvl: manager.getAchievements()) {
            
            // Checks whether score completes the achievement
            if (score >= lvl.getScore()) {
                achieved.add(lvl);
            }
        }
        return achieved;
    }
    
    // Returns list of uncompleted achievements that have been completed
    public List<Achievements> getUncompletedAchievements(int score) {
        
        // Iterates through achievement levels from manager
        for (Achievements lvl: manager.getAchievements()) {

            // Checks whether score completes the achievement
            if (score < lvl.getScore()) {
                unachieved.add(lvl);
            }
        }
        return unachieved;
    }

    // Returns entire list of achievements
    public List<Achievements> getAllAchievements() {
        return manager.getAchievements();
    }
    
}
