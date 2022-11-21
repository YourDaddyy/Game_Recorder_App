package com.example.projectscandium.model;

import static org.junit.jupiter.api.Assertions.*;

class AchievementsTest {

    @org.junit.jupiter.api.Test
    void testGetAchievementName() {
        Achievements achievement = new Achievements();
        assertEquals("Kitten Prodigy", achievement.getAchievementName(4));
    }

    @org.junit.jupiter.api.Test
    void testSetBadAchievementNameThrow() {
        Achievements achievement = new Achievements();

        // Null value exception
        assertThrows(IllegalArgumentException.class, () -> achievement.setAchievementName(null));

        // Illegal value exception
        assertThrows(IllegalArgumentException.class, () -> achievement.setAchievementName("Lizard"));
    }

    @org.junit.jupiter.api.Test
    void testBadDiffLevelThrow() {
        Achievements achievement = new Achievements();

        // Null value exception
        assertThrows(IllegalArgumentException.class, () -> achievement.setDiffLevel(null, 0,1, 1));

        // Illegal bounds exceptions
        assertThrows(IllegalArgumentException.class, () -> achievement.setDiffLevel("Easy", 3,1, 1));
        assertThrows(IllegalArgumentException.class, () -> achievement.setDiffLevel("Easy", -3,1, 1));

        // Illegal player number exception
        assertThrows(IllegalArgumentException.class, () -> achievement.setDiffLevel("Easy", 0,1, 0));
    }

    @org.junit.jupiter.api.Test
    void testGetAchievementValue() {

        // Test achievement default value
        Achievements achievement = new Achievements();

        // Test achievement value after setting scores with normal difficulty
        Achievements achievement2 = new Achievements();
        achievement2.setDiffLevel("Normal",1,9,1);

        // Test achievement value after setting scores with easy difficulty
        Achievements achievement3 = new Achievements();
        achievement3.setDiffLevel("Easy",2,10,1);

        // Test achievement value after setting scores with hard difficulty
        Achievements achievement4 = new Achievements();
        achievement4.setDiffLevel("Hard",2,10,2);

        // Test achievement value after changing player num scores with normal difficulty
        Achievements achievement5 = new Achievements();
        achievement5.setDiffLevel("Normal",1,9,2);


        assertAll("Correctly calculates scores, ",
                // Default value
                ()->assertEquals(0, achievement.getAchievementValue(3)),
                // Check if lowest level is always zero no matter the lower bound for normal
                ()->assertEquals(0, achievement2.getAchievementValue(0)),
                // Check expected lowest level for normal
                ()->assertEquals(1, achievement2.getAchievementValue(1)),
                // Checks an intermediate level for normal
                ()->assertEquals(3, achievement2.getAchievementValue(3)),
                // Checks the expected upper bound
                ()->assertEquals(9, achievement2.getAchievementValue(9)),
                // Checks the upper bound for easy
                ()->assertEquals(10 * 0.75, achievement3.getAchievementValue(9)),
                // Checks the upper bound for hard
                ()->assertEquals(10 * 1.25 * 2, achievement4.getAchievementValue(9)),
                // Checks the upper bound with player score changed for normal
                ()->assertEquals(9 * 2, achievement5.getAchievementValue(9))
        );

    }

    @org.junit.jupiter.api.Test
    void testSetAchievementValue() {
        Achievements achievement = new Achievements();
        int index = 1;
        int score = 2;
        achievement.setAchievementValue(index, score);
        assertEquals(score, achievement.getAchievementValue(index));
    }

    @org.junit.jupiter.api.Test
    void testGetLowerScoreBound() {
        // Test achievement default value
        Achievements achievement = new Achievements();

        // Test achievement value after setting scores with normal difficulty
        Achievements achievement2 = new Achievements();
        achievement2.setDiffLevel("Normal",1,9,1);

        // Test achievement value after setting scores with easy difficulty
        Achievements achievement3 = new Achievements();
        achievement3.setDiffLevel("Easy",2,10,1);

        // Test achievement value after setting scores with hard difficulty
        Achievements achievement4 = new Achievements();
        achievement4.setDiffLevel("Hard",2,10,2);

        // Test achievement value after changing player num scores with normal difficulty
        Achievements achievement5 = new Achievements();
        achievement5.setDiffLevel("Normal",1,9,2);

        assertAll("Correctly calculates boundary, ",
                ()->assertEquals(0, achievement.getLowerScoreBound()),
                ()->assertEquals(1, achievement2.getLowerScoreBound()),
                ()->assertEquals(2, achievement3.getLowerScoreBound()),
                ()->assertEquals(4, achievement4.getLowerScoreBound()),
                ()-> assertEquals(2, achievement5.getLowerScoreBound())
        );

    }

    @org.junit.jupiter.api.Test
    void testGetUpperScoreBound() {
        // Test achievement default value
        Achievements achievement = new Achievements();

        // Test achievement value after setting scores with normal difficulty
        Achievements achievement2 = new Achievements();
        achievement2.setDiffLevel("Normal",1,9,1);

        // Test achievement value after setting scores with easy difficulty
        Achievements achievement3 = new Achievements();
        achievement3.setDiffLevel("Easy",2,10,1);

        // Test achievement value after setting scores with hard difficulty
        Achievements achievement4 = new Achievements();
        achievement4.setDiffLevel("Hard",2,10,2);

        // Test achievement value after changing player num scores with normal difficulty
        Achievements achievement5 = new Achievements();
        achievement5.setDiffLevel("Normal",1,9,2);

        assertAll("Correctly calculates boundary, ",
                ()->assertEquals(0, achievement.getUpperScoreBound()),
                ()->assertEquals(9, achievement2.getUpperScoreBound()),
                ()->assertEquals(10, achievement3.getUpperScoreBound()),
                ()->assertEquals(20, achievement4.getUpperScoreBound()),
                ()-> assertEquals(18, achievement5.getUpperScoreBound())
        );
    }

    @org.junit.jupiter.api.Test
    void testGetAchievement() {
        Achievements achievement = new Achievements();
        int lowestScore = 0;
        int highestScore = 100;
        int midScore = 3;
        int lower = 1;
        int upper = 9;
        int players = 1;
        String difficulty = "Normal";
        achievement.setDiffLevel(difficulty,lower,upper,players);

        assertAll("Correctly assigns achievement level, ",
                // Lowest level
                ()->assertEquals("Novice Cat", achievement.getAchievement(lowestScore)),
                // Highest level
                ()->assertEquals("Aye Aye Cat-tain", achievement.getAchievement(highestScore)),
                // Mid level
                ()->assertEquals("Momma Cat", achievement.getAchievement(midScore))
        );
    }

    @org.junit.jupiter.api.Test
    void testBadGetAchievementInputThrow() {
        Achievements achievement = new Achievements();

        // Negative Value exception
        assertThrows(IllegalArgumentException.class, () -> achievement.getAchievement(-1));

    }

    @org.junit.jupiter.api.Test
    void getAchievementIndex() {
        Achievements achievement = new Achievements();
        int lowestScore = 0;
        int highestScore = 100;
        int midScore = 3;
        int lower = 1;
        int upper = 9;
        int players = 1;
        String difficulty = "Normal";
        achievement.setDiffLevel(difficulty,lower,upper,players);

        assertAll("Correctly assigns achievement level, ",
                // Lowest level
                ()->assertEquals(0, achievement.getAchievementIndex(lowestScore)),
                // Highest level
                ()->assertEquals(9, achievement.getAchievementIndex(highestScore)),
                // Mid level
                ()->assertEquals(3, achievement.getAchievementIndex(midScore))
        );
    }

    @org.junit.jupiter.api.Test
    void testBadGetAchievementIndexInputThrow() {
        Achievements achievement = new Achievements();

        // Negative Value exception
        assertThrows(IllegalArgumentException.class, () -> achievement.getAchievementIndex(-1));

    }
}