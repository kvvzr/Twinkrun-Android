package net.twinkrun.twinkrun.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Result extends Object {

    private int mScore;
    private Map<Role, List<Integer>> mTransition;
    private List<Player> mRanking;
    private Date mDate;

    public Result(int score, Map<Role, List<Integer>> transition) {
        mTransition = transition;
        mScore = score;
        mDate = new Date();
    }

    public Result(int score, Map<Role, List<Integer>> transition, List<Player> ranking) {
        mScore = score;
        mTransition = transition;
        mRanking = ranking;
        mDate = new Date();
    }

    public int getScore() {
        return mScore;
    }

    public Map<Role, List<Integer>> getTransition() {
        return mTransition;
    }

    public List<Player> getRanking() {
        return mRanking;
    }

    public void setRanking(List<Player> ranking) {
        mRanking = ranking;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
