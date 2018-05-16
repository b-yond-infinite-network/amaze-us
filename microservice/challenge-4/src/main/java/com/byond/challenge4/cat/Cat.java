package com.byond.challenge4.cat;

import java.util.UUID;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Cat {

    private final String name;
    private Mood mood;

    public Cat() {
        //A bit rude I know, but I do like cats
        name = UUID.randomUUID().toString();
        mood = Mood.getRandomMood();
    }

    public String getName() {
        return name;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
            append("name", name).
            append("mood", mood).
            toString();
    }
}
