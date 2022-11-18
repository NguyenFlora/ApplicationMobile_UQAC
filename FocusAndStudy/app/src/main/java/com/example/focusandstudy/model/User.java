package com.example.focusandstudy.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private int mId;
    private String mUsername;
    private String mEmail;
    private String mPassword;
    private int mDailyTime;
    private int mWeeklyTime;
    private int mDayStreak;
    private int mNbBadges;
    private int mXP;

    public User(){

    }

    public User(int id, String username, String email, String password, int dailyTime, int weeklyTime, int dayStreak, int nbBadges, int XP) {
        mId = id;
        mUsername = username;
        mEmail = email;
        mPassword = password;
        mDailyTime = dailyTime;
        mWeeklyTime = weeklyTime;
        mDayStreak = dayStreak;
        mNbBadges = nbBadges;
        mXP = XP;
    }

    protected User(Parcel in) {
        mId = in.readInt();
        mUsername = in.readString();
        mEmail = in.readString();
        mPassword = in.readString();
        mDailyTime = in.readInt();
        mWeeklyTime = in.readInt();
        mDayStreak = in.readInt();
        mNbBadges = in.readInt();
        mXP = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mId);
        out.writeString(mUsername);
        out.writeString(mEmail);
        out.writeString(mPassword);
        out.writeInt(mDailyTime);
        out.writeInt(mWeeklyTime);
        out.writeInt(mDayStreak);
        out.writeInt(mNbBadges);
        out.writeInt(mXP);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

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

    public int getDayStreak() {
        return mDayStreak;
    }

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