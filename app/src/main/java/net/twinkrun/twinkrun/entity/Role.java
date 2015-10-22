package net.twinkrun.twinkrun.entity;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import net.twinkrun.twinkrun.R;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Role extends Object {

    String mName;
    int mColor;
    int mCount;
    int mTime;
    int mScore;

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

    @Override
    public int hashCode() {
        return new HashCodeBuilder(25, 21)
                .append(mName)
                .append(mColor)
                .append(mCount)
                .append(mTime)
                .append(mScore)
                .toHashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Role)) {
            return false;
        }
        if (o == this) {
            return true;
        }
        Role role = (Role) o;
        return new EqualsBuilder()
                .append(mName, role.mName)
                .append(mColor, role.mColor)
                .append(mCount, role.mCount)
                .append(mTime, role.mTime)
                .append(mScore, role.mScore)
                .isEquals();
    }
}
