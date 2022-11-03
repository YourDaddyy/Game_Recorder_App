package com.example.projectscandium.model;

/**
 * Represents the possible achievements that a game configuration can have.
 */
public class Achievements {

    private final String name;
    private final int score;

    public Achievements(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Achievements{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}

