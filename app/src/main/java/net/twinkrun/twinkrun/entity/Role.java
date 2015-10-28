package net.twinkrun.twinkrun.entity;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import net.twinkrun.twinkrun.R;

public class Role extends Object {

    private String mName;
    private int mColor;
    private int mCount;
    private int mTime;
    private int mScore;

    public Role(String name, int color, int count, int time, int score) {
        mName = name;
        mColor = color;
        mCount = count;
        mTime = time;
        mScore = score;
    }

    public static Role red(Context context, int count, int time, int score) {
        return new Role("Red", ContextCompat.getColor(context, R.color.twinkrunRed), count, time, score);
    }

    public static Role green(Context context, int count, int time, int score) {
        return new Role("Green", ContextCompat.getColor(context, R.color.twinkrunGreen), count, time, score);
    }

    public static Role black(Context context, int count, int time, int score) {
        return new Role("Black", ContextCompat.getColor(context, R.color.twinkrunBlack), count, time, score);
    }
}
