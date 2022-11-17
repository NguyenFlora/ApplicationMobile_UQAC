package com.example.focusandstudy.model;

import android.icu.util.TimeZone;
import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String mUsername;
    private String mEmail;
    private String mPassword;

    public User() {
    }

    public User(String username, String email, String password) {
        mUsername = username;
        mEmail = email;
        mPassword = password;
    }

    protected User(Parcel in) {
        mUsername = in.readString();
        mEmail = in.readString();
        mPassword = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mUsername);
        out.writeString(mEmail);
        out.writeString(mPassword);
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

}