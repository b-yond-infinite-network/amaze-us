package com.byond.challenge4.cat;

import java.util.EnumSet;
import java.util.Random;
import org.apache.commons.lang.builder.ToStringBuilder;

public enum Mood {
    GROWL(1, "grr"),
    HISS(2, "kssss"),
    PURR(3, "rrrrr"),
    THROWGLASS(4, "cling bling"),
    ROLLONFLOOR(5, "fffff"),
    SCRATCHCHAIRS(6, "gratgrat"),
    LOOKDEEPINEYES(7, "-o-o-___--");

    //Assigning ids to each mood so less data on the wire !
    private final int id;
    private final String mood;

    Mood(int id, String mood) {
        this.id = id;
        this.mood = mood;
    }

    public int getId() {
        return id;
    }

    public String getMood() {
        return mood;
    }

    public static Mood fromId(int id) {
        return EnumSet.allOf(Mood.class).stream().filter(mood -> mood.getId() == id)
            .findFirst().orElseThrow(() -> new IllegalArgumentException("No mood found with id = " + id));
    }

    public static Mood getRandomMood() {
        return values()[new Random().nextInt(values().length)];
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
            append("name", name()).
            append("id", id).
            append("mood", mood).
            toString();
    }
}
