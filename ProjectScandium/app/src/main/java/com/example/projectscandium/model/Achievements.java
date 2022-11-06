package com.example.projectscandium.model;

import androidx.annotation.NonNull;

/**
 * Represents the possible achievements that a game configuration can have.
 */
public class Achievements {

    private final String name;
    private final double score;
    private boolean completion;

    public Achievements(String name, double score) {
        this.name = name;
        this.score = score;
        this.completion = false;
    }

    public boolean isCompletion() {
        return completion;
    }

    public void setCompletion(boolean completion) {
        this.completion = completion;
    }

    public String getName() {
        return name;
    }

    public double getScore() {
        return score;
    }

    @NonNull
    @Override
    public String toString() {
        return "Achievements{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}

