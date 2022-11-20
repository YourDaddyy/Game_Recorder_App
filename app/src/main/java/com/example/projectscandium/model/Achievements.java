package com.example.projectscandium.model;

/**
 * Achievement (Class)
 * Purpose: Represents the possible achievements that a game configuration can
 * have.
 */
public class Achievements {
    private final String[] catTheme = { "Novice Cat", "Average Joe Cat", "Daddy Cat", "Momma Cat", "Kitten Prodigy",
            "Silly Cat", "Kitten Army", "Flabbergast Cat", "Nyan Kitty", "Aye Aye Cat-tain" };
    private final String[] dogTheme = { "Novice Dog", "Average Joe Dog", "Daddy Dog", "Momma Dog", "Puppy Prodigy",
            "Silly Dog", "Puppy Army", "Flabbergast Dog", "Nyan Doggy", "Aye Aye Dog-tain" };
    private final String[] birdTheme = { "Novice Bird", "Average Joe Bird", "Daddy Bird", "Momma Bird", "Bird Prodigy",
            "Silly Bird", "Bird Army", "Flabbergast Bird", "Nyan Birdie", "Aye Aye Bird-tain" };

    // array of achievements names
    public String[] achievements = { "Novice Cat", "Average Joe Cat", "Daddy Cat", "Momma Cat", "Kitten Prodigy",
            "Silly Cat", "Kitten Army", "Flabbergast Cat", "Nyan Kitty", "Aye Aye Cat-tain" };
    private String theme;

    // array of int values for each achievement
    private final double[] achievementValues = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

    // variable to store the lower bound and upper bound of the achievement scores
    private double lowerScoreBound;
    private double upperScoreBound;

    // getAchievementName
    // Purpose: returns the name of the achievement at the given index
    // Return: String
    public String getAchievementName(int index) {
        return achievements[index];
    }

    // setAchievementName
    // Purpose: sets the name of the achievement name based on theme
    // Return: void
    public void setAchievementName(String theme) {
        if (theme != null) {
            if (theme.equals("Dog")) {
                this.achievements = dogTheme;
            }
            if (theme.equals("Cat")) {
                this.achievements = catTheme;
            }
            if (theme.equals("Bird")) {
                this.achievements = birdTheme;
            }
        }
    }

    // setDiffLevel
    // Purpose: Updates the score bound based on diff
    // Return: void
    public void setDiffLevel(String diffLevel, int lower, int upper, int numPlayers) {
        double score = 1;
        if (diffLevel.equals("Easy"))
            score = 0.75;
        if (diffLevel.equals("Hard"))
            score = 1.25;
        // set worse score possible
        setAchievementValue(0, 0);

        this.lowerScoreBound = (double) lower * numPlayers;
        this.upperScoreBound = (double) upper * numPlayers;

        double range = upperScoreBound - lowerScoreBound;
        double increment = (double) range / (9 - 1);
        for (int i = 1; i < 10; i++) {
            double points = lowerScoreBound + (increment * (i - 1));
            points = points * score;
            setAchievementValue(i, points);
        }
    }

    // getAchievementValue
    // Purpose: returns the value of the achievement at the given index
    // Return: double
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
    // Return: double
    public double getLowerScoreBound() {
        return lowerScoreBound;
    }

    // getUpperScoreBound
    // Purpose: returns the upper bound of the achievement scores
    // Return: double
    public double getUpperScoreBound() {
        return upperScoreBound;
    }

    // getAchievement
    // Purpose: returns the achievement name based on the score
    // Return: String
    public String getAchievement(int score) {
        if ((double) score < achievementValues[1]) {
            return achievements[0];
        }
        // if the score is greater than the upper bound, return the last achievement
        if ((double) score > (double) achievementValues[9]) {
            return achievements[9];
        }
        // if the score is between the lower and upper bound, return the achievement
        // that corresponds to the score
        for (int i = 0; i < 10; i++) {
            if ((double) score <= achievementValues[i]) {
                return achievements[i];
            }
        }
        return achievements[9];
    }

    // getAchievementIndex
    // Purpose: returns the index of the achievement that the given score belongs to
    // Return: int
    public int getAchievementIndex(int score) {
        if ((double) score < achievementValues[1]) {
            return 0;
        }
        // if the score is greater than the upper bound, return the last achievement
        if ((double) score > achievementValues[9]) {
            return 9;
        }
        // if the score is between the lower and upper bound, return the achievement
        // that corresponds to the score
        for (int i = 0; i < 10; i++) {
            if ((double) score <= achievementValues[i]) {
                return i;
            }
        }
        return 9;
    }
}
