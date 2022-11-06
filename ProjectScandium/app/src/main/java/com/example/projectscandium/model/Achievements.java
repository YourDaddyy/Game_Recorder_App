package com.example.projectscandium.model;

import androidx.annotation.NonNull;

/**
 * Represents the possible achievements that a game configuration can have.
 */
public class Achievements {
    // array of achievements names
    public String[] achievements = { "Novice Cat", "Average Joe Cat", "Daddy Cat", "Momma Cat", "Kitten Prodigy",
            "Silly Cat", "Ktten Army", "Flabbergast Cat", "Nyan Kitty", "Aye Aye Cat-tain" };

    // aray of int values for each achievement
    private int[] achievementValues = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

    private int lowerScoreBound;
    private int upperScoreBound;

    // function to get the name of the achievement
    public String getAchievementName(int index) {
        return achievements[index];
    }

    // function to get the value of the achievement
    public int getAchievementValue(int index) {
        return achievementValues[index];
    }

    // function to set the value of the achievement
    public void setAchievementValue(int index, int value) {
        this.achievementValues[index] = value;
    }

    // function to set the lower and upper bounds of the score
    public void setScoreBounds(int lower, int upper, int numPlayers) {
        this.lowerScoreBound = lower * numPlayers;
        this.upperScoreBound = upper * numPlayers;

        int range = upperScoreBound - lowerScoreBound;
        int increment = range / 10;
        for (int i = 0; i < 10; i++) {
            int points = lowerScoreBound + (increment * i);
            setAchievementValue(i, points);
        }
    }

    // function to get the lower bound of the score
    public int getLowerScoreBound() {
        return lowerScoreBound;
    }

    // function to get the upper bound of the score
    public int getUpperScoreBound() {
        return upperScoreBound;
    }

    // function to get achievement based on the score. If the score is less than the
    // lower bound, return the first achievement
    public String getAchievement(int score) {
        if (score < lowerScoreBound) {
            return achievements[0];
        }
        // if the score is greater than the upper bound, return the last achievement
        if (score > upperScoreBound) {
            return achievements[9];
        }
        // if the score is between the lower and upper bound, return the achievement
        // that corresponds to the score
        for (int i = 0; i < 10; i++) {
            if (score < achievementValues[i]) {
                return achievements[i];
            }
        }
        return achievements[9];
    }

}
