package com.example.projectscandium.model;

import static org.junit.jupiter.api.Assertions.*;

class AchievementsTest {

    @org.junit.jupiter.api.Test
    void testGetAchievementName() {
        Achievements achievement = new Achievements();
        assertEquals("Kitten Prodigy", achievement.getAchievementName(4));
    }
//
//    @org.junit.jupiter.api.Test
//    void testGetValidValueAssertAll() {
//        Achievements achievement = new Achievements();
//        assertAll("Correctly gets values ",
//                ()-> assertEquals("Kitten Prodigy", achievement.getAchievementName(4)),
//                ()->  assertEquals(3, achievement.getPoints()),
//                ()->  assertEquals(2, achievement.getWagers())
//        );
//    }

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
    void getAchievementValue() {
    }

    @org.junit.jupiter.api.Test
    void setAchievementValue() {
    }

    @org.junit.jupiter.api.Test
    void getLowerScoreBound() {
    }

    @org.junit.jupiter.api.Test
    void getUpperScoreBound() {
    }

    @org.junit.jupiter.api.Test
    void getAchievement() {
    }

    @org.junit.jupiter.api.Test
    void getAchievementIndex() {
    }
}