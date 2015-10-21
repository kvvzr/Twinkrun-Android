package net.twinkrun.twinkrun.entity;

import android.support.annotation.NonNull;

public class Player extends Object {

    private String mName;
    private int mSeed;

    public Player(@NonNull String name, int seed) {
        mName = name;
        mSeed = seed;
    }

    public static Player fromBleName(@NonNull String text) {
        String[] data = text.split(",");
        if (data.length != 2) {
            throw new IllegalArgumentException();
        }
        return new Player(data[0], Integer.valueOf(data[1]));
    }

    public @NonNull String getName() {
        return mName;
    }

    public int getSeed() {
        return mSeed;
    }
}
