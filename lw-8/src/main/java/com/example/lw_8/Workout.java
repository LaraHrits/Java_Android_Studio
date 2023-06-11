package com.example.lw_8;

public class Workout {
    private String name;

    public static final Workout[] workouts = {
            new Workout("Dancing"),
            new Workout("Running"),
            new Workout("Burpy"),
            new Workout("Swimming"),
    };

    private Workout(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}