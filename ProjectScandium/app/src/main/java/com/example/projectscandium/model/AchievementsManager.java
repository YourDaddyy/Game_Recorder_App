package com.example.projectscandium.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Manages a collection of achievements.
 */
public class AchievementsManager implements Iterable<Achievements> {

    private final List<Achievements> achievements = new ArrayList<>();

    public AchievementsManager() {
    }

    // Adding new achievements
    public void add(Achievements achievement) {
        achievements.add(achievement);
    }

    // Get the list of achievements.
    public List<Achievements> getAchievements() {
        return achievements;
    }

    // Retrieving a specific achievements by its index
    public Achievements getAchievements(int index) {
        return achievements.get(index);
    }

    // Set a specific achievements by its index
    public void setAchievements(int index, Achievements achievement) {
        achievements.set(index, achievement);
    }

    // Removing a achievements by its index
    public void removeAchievements(int index) {
        achievements.remove(index);
    }

    @NonNull
    @Override
    public Iterator<Achievements> iterator() {
        return achievements.iterator();
    }
}
