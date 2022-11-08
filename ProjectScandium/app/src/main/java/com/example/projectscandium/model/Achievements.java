package com.example.projectscandium.model;

/**
 * Achievement (Class)
 * Purpose: Represents the possible achievements that a game configuration can have.
 */
public class Achievements {
    // array of achievements names
    public String[] achievements = { "Novice Cat", "Average Joe Cat", "Daddy Cat", "Momma Cat", "Kitten Prodigy",
            "Silly Cat", "Kitten Army", "Flabbergast Cat", "Nyan Kitty", "Aye Aye Cat-tain" };

    // array of int values for each achievement
    private final double[] achievementValues = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

    // variable to store the lower bound and upper bound of the achievement scores
    private int lowerScoreBound;
    private int upperScoreBound;

    // getAchievementName
    // Purpose: returns the name of the achievement at the given index
    // Return: String
    public String getAchievementName(int index) {
        return achievements[index];
    }

    // getAchievementValue
    // Purpose: returns the value of the achievement at the given index
    // Return: int
    public double getAchievementValue(int index) {
        return achievementValues[index];
    }

    // setAchievementValue
    // Purpose: sets the value of the achievement at the given index
    // Return: void
    public void setAchievementValue(int index, double value) {
        this.achievementValues[index] = value;
    }

    // getLowerScoreBound
    // Purpose: returns the lower bound of the achievement scores
    // Return: int
    public void setScoreBounds(int lower, int upper, int numPlayers) {

        // set worse score possible
        setAchievementValue(0, 0);

        this.lowerScoreBound = lower * numPlayers;
        this.upperScoreBound = upper * numPlayers;

        int range = upperScoreBound - lowerScoreBound;
        double increment = (double)range / (9 - 1);
        for (int i = 1; i < 10; i++) {
            double points = lowerScoreBound + (increment * (i - 1));
            setAchievementValue(i, points);
        }
    }



    // getLowerScoreBound
    // Purpose: returns the lower bound of the achievement scores
    // Return: int
    public int getLowerScoreBound() {
        return lowerScoreBound;
    }

    // getUpperScoreBound
    // Purpose: returns the upper bound of the achievement scores
    // Return: int
    public int getUpperScoreBound() {
        return upperScoreBound;
    }

    // getAchievement
    // Purpose: returns the achievement name based on the score
    // Return: String
    public String getAchievement(int score) {
        double doubleScore = score;

        if (doubleScore < (double)lowerScoreBound) {
            return achievements[0];
        }
        // if the score is greater than the upper bound, return the last achievement
        if (doubleScore > (double)upperScoreBound) {
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

    // getAchievementIndex
    // Purpose: returns the index of the achievement that the given score belongs to
    // Return: int
    public int getAchievementIndex(int score) {
        double doubleScore = (double)score;
        double doubleLower = lowerScoreBound;
        double doubleUpper = upperScoreBound;

        if (doubleScore < doubleLower) {
            return 0;
        }
        // if the score is greater than the upper bound, return the last achievement
        if (doubleScore > doubleUpper) {
            return 9;
        }
        // if the score is between the lower and upper bound, return the achievement
        // that corresponds to the score
        for (int i = 0; i < 10; i++) {
            if (doubleScore < achievementValues[i]) {
                return i;
            }
        }
        return 9;
    }
}
