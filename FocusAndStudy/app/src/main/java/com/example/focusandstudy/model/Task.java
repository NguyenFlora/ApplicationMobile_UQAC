package com.example.focusandstudy.model;

import java.util.Date;

public class Task {
    private int mId;
    private String mType;
    private String mName;
    private String mDescription;
    private Date mDate;
    private String mStatus;
    private User mUser;

    public Task() {
    }

    public Task(int id, String type, String name, String description, Date date, String status, User user) {
        mId = id;
        mType = type;
        mName = name;
        mDescription = description;
        mDate = date;
        mStatus = status;
        mUser = user;
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

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }


}
