package com.example.projectscandium.model;

/**
 * A list of the achievements and base scores.
 */
public enum AchievementsList {

    LVL1("Average Joe Cat", 0),
    LVL2("Momma Cat", 5),
    LVL3("Daddy Cat", 10),
    LVL4("Kitten Prodigy", 15),
    LVL5("Silly Cat", 20),
    LVL6("Ktten Army", 25),
    LVL7("Flabbergast Cat", 30),
    LVL8("Nyan Kitty", 35);

    private final String level;
    private final int baseScore;

    public String getLevelName()
    {
        return this.level;
    }

    public int getBaseScore()
    {
        return this.baseScore;
    }

    private AchievementsList(String level, int baseScore) {
        this.level = level;
        this.baseScore = baseScore;
    }

}
