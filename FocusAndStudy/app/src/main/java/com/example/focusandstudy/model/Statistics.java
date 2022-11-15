package com.example.focusandstudy.model;

public class Statistics {
    private int mId;
    private static int nextId = 0;
    private int mDailyTime;
    private int mWeeklyTime;
    private int mDayStreak;
    private int mNbBadges;
    private int mXP;

    public Statistics() {
        mDailyTime = 0;
        mWeeklyTime = 0;
        mDayStreak = 0;
        mNbBadges = 0;
        mXP = 0;
        mId = nextId;
        nextId++;
    }

    public Statistics(int dailyTime, int weeklyTime, int dayStreak, int nbBadges, int XP) {
        mDailyTime = dailyTime;
        mWeeklyTime = weeklyTime;
        mDayStreak = dayStreak;
        mNbBadges = nbBadges;
        mXP = XP;

    }

    public int getId() { return mId; }

    public void setId(int id) { mId = id; }

    public int getNextId() { return nextId; }

    public void setNextId(int nextId) { Statistics.nextId = nextId; }

    public int getDailyTime() {
        return mDailyTime;
    }

    public void setDailyTime(int dailyTime) {
        mDailyTime = dailyTime;
    }

    public int getWeeklyTime() {
        return mWeeklyTime;
    }

    public void setWeeklyTime(int weeklyTime) {
        mWeeklyTime = weeklyTime;
    }

    public int getDayStreak() { return mDayStreak; }

    public void setDayStreak(int dayStreak) {
        mDayStreak = dayStreak;
    }

    public int getNbBadges() {
        return mNbBadges;
    }

    public void setNbBadges(int nbBadges) {
        mNbBadges = nbBadges;
    }

    public int getXP() {
        return mXP;
    }

    public void setXP(int XP) {
        mXP = XP;
    }

}
