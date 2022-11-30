package com.example.projectscandium.model;

/**
 * Achievement (Class)
 * Purpose: Represents the possible achievements that a game configuration can
 * have.
 */
public class Achievements {

    // array of achievement themes
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
        if (theme == null) {
            throw new IllegalArgumentException("Theme cannot be null");
        }
        else{
            switch (theme) {
                case "Dog":
                    this.achievements = dogTheme;
                    break;
                case "Cat":
                    this.achievements = catTheme;
                    break;
                case "Bird":
                    this.achievements = birdTheme;
                    break;
                default:
                    throw new IllegalArgumentException("Theme must be Dog, Cat, or Bird");
            }
        }
    }

    // setDiffLevel
    // Purpose: Updates the score bound based on diff
    // Return: void
    public void setDiffLevel(String diffLevel, int lower, int upper, int numPlayers) {
        // empty input
        if (diffLevel == null) {
            throw new IllegalArgumentException("Difficulty level cannot be null");
        }
        // check if lower bound is bigger than upper
        else if (lower > upper) {
            throw new IllegalArgumentException("Lower score bound can not be lower than upper");
        }
        // check if bound is negative
        else if (lower < 0) {
            throw new IllegalArgumentException("Score bound can not be negative");
        }
        // check if number of players is greater than zero
        else if (numPlayers <= 0) {
            throw new IllegalArgumentException("Players must be greater than zero");
        }
        else {
            double score = 1;
            if (diffLevel.equals("Easy"))
                score = 0.75;
            if (diffLevel.equals("Hard"))
                score = 1.25;
            // set worse score possible
            setAchievementValue(0, 0);

            setLowerScoreBound(lower, numPlayers);
            setUpperScoreBound(upper, numPlayers);

            double range = upperScoreBound - lowerScoreBound;
            double increment = range / (9 - 1);
            for (int i = 1; i < 10; i++) {
                double points = lowerScoreBound + (increment * (i - 1));
                points = points * score;
                setAchievementValue(i, points);
            }
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

    // setLowerScoreBound
    // Purpose: sets the lower bound of the achievement scores
    // Return: void
    public void setLowerScoreBound(int lowerBound, int players) {
        this.lowerScoreBound = (double) (lowerBound * players);
    }

    // getUpperScoreBound
    // Purpose: returns the upper bound of the achievement scores
    // Return: double
    public double getUpperScoreBound() {
        return upperScoreBound;
    }

    // setUpperScoreBound
    // Purpose: sets the upper bound of the achievement scores
    // Return: void
    public void setUpperScoreBound(int upperBound, int players) {
        this.upperScoreBound = (double) (upperBound * players);
    }

    // getAchievement
    // Purpose: returns the achievement name based on the score
    // Return: String
    public String getAchievement(int score) {
        if (score < 0) {
            throw new IllegalArgumentException("Score can not be negative");
        }
        else{
            // if the score is between the lower and upper bound, return the achievement
            // that corresponds to the score
            int index = 0;
            for (int i = 0; i < 10; i++) {
                if ((double) score >= achievementValues[i]) {
                    index = i;
                } else {
                    break;
                }
            }
            return achievements[index];
        }
    }

    // getAchievementIndex
    // Purpose: returns the index of the achievement that the given score belongs to
    // Return: int
    public int getAchievementIndex(int score) {
        if (score < 0) {
            throw new IllegalArgumentException("Score can not be negative");
        }
        else {
            // if the score is between the lower and upper bound, return the achievement
            // that corresponds to the score
            int index = 0;
            for (int i = 0; i < 10; i++) {
                if ((double) score >= achievementValues[i]) {
                    index = i;
                } else {
                    break;
                }
            }
            return index;
        }
    }

    // getPointsRqd
    // Purpose: returns points to get the next achievement
    // Return: double
    public double getPointsRqd(int score) {

        // Gets the current achievement
        int index = getAchievementIndex(score);
        double points;

        // Determines whether the highest lvl (index 9) is achieved
        if (index == 9) {
            points = 0;
        }
        // Returns the rqd points for the next lvl
        else {
            points = achievementValues[index + 1] - score;
        }
        return points;

    }


    // getNextAchievement
    // Purpose: returns the next achievement name
    // Return: String
    public String getNextAchievement(int score) {

        // Gets the current achievement
        int index = getAchievementIndex(score);
        String name = "";

        // Determines whether the highest lvl (index 9) is achieved
        if (index == 9) {
            name = "\n\nYou've achieved the highest level\n";
        }
        // Returns the next level name
        else {
            name = "\n\nNext Achievement: " + achievements[index + 1] + "\n";
        }
        return name;
    }
}
