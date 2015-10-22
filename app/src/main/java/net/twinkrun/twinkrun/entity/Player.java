package net.twinkrun.twinkrun.entity;

import android.support.annotation.NonNull;

public class Player extends Object {

    private String mName;
    private int mSeed;
    private boolean mPlayWith = true;

    public Player(@NonNull String text) {
        setFromBleName(text);
    }

    public Player(@NonNull String name, int seed) {
        mName = name;
        mSeed = seed;
    }

    public void setFromBleName(@NonNull String text) throws IllegalArgumentException {
        String[] data = text.split(",");
        if (data.length != 2) {
            throw new IllegalArgumentException();
        }
        mName = data[0];
        mSeed = Integer.valueOf(data[1]);
    }

    public @NonNull String getName() {
        return mName;
    }

    public void setName(@NonNull String name) {
        mName = name;
    }

    public int getSeed() {
        return mSeed;
    }

    public boolean playWith() {
        return mPlayWith;
    }

    public void setPlayWith(boolean playWith) {
        mPlayWith = playWith;
    }

    public void togglePlayWith() {
        mPlayWith = !mPlayWith;
    }
}
