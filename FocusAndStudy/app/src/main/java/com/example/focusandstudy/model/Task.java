package com.example.focusandstudy.model;

import java.time.LocalDate;
import java.util.Date;

public class Task {
    private int mId;
    private String mType;
    private String mName;
    private String mDescription;
    private String mDate;
    private String mStatus;
    private int mUserID;

    public Task() {
    }

    public Task(int id, String type, String name, String description, String date, String status, int userId) {
        mId = id;
        mType = type;
        mName = name;
        mDescription = description;
        mDate = date;
        mStatus = status;
        mUserID = userId;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public int getUserID() {
        return mUserID;
    }

    public void setUserID(int userID) {
        mUserID = userID;
    }
}
