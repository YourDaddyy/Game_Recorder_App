package com.example.projectscandium.model;

import static org.junit.jupiter.api.Assertions.*;

class AchievementsTest {

    @org.junit.jupiter.api.Test
    void getAchievementName() {
        Achievements achievement = new Achievements();
        assertEquals("Kitten Prodigy", achievement.getAchievementName(4));
    }

    @org.junit.jupiter.api.Test
    void setAchievementName() {
        Achievements achievement = new Achievements();
        assertThrows(IllegalArgumentException.class, () -> achievement.setAchievementName(null));
    }

    @org.junit.jupiter.api.Test
    void setDiffLevel() {
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