package com.example.projectscandium.model;

/**
 * Manages the 8 achievement levels based on number of players.
 */
public class AchievementsInterface {

    // A manager for the list of achievements.
    private static AchievementsManager manager;

    // The number of players for this game.
    private final int playerCount;

    // Enum of predetermined achievements for the game.
    AchievementsList[] achievements = AchievementsList.values();

    public AchievementsInterface(int playerCount) {
        manager = AchievementsManager.getInstance();
        this.playerCount = playerCount;
        createAchievement();
    }

    // Adjust the 8 levels based on number of players and creates achievement levels
    private void createAchievement() {

        // Iterates through enum data and populates new achievement levels into manager
        for (AchievementsList lvl: achievements) {

            // Adjusts base score to number of players
            int adjustedScore = lvl.getBaseScore() * playerCount;
            manager.add(new Achievements(lvl.getLevelName(),lvl.getBaseScore()));

        }
    }
}
