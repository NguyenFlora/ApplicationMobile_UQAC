package com.example.focusandstudy.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.focusandstudy.model.Statistics;

import java.io.IOException;

public class StatisticsDB extends SQLiteOpenHelper {
    public static final String STATISTICS_TABLE = " STATISTICS_TABLE ";
    public static final String COLUMN_STATISTICS_ID = " COLUMN_STATISTICS_ID ";
    public static final String COLUMN_DAILY_TIME = " COLUMN_DAILY_TIME ";
    public static final String COLUMN_WEEKLY_TIME = " COLUMN_WEEKLY_TIME ";
    public static final String COLUMN_DAY_STREAK = " COLUMN_DAY_STREAK ";
    public static final String COLUMN_NB_BADGES = " COLUMN_NB_BADGES ";
    public static final String COLUMN_XP = " COLUMN_XP ";
    private Context context;

    public StatisticsDB(@Nullable Context context) {
        super(context, "focusandstudy.db", null, 16);
    }

        @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + STATISTICS_TABLE + " ("
                + COLUMN_STATISTICS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  "
                + COLUMN_DAILY_TIME + " INTEGER, "
                + COLUMN_WEEKLY_TIME + " INTEGER, "
                + COLUMN_DAY_STREAK + " INTEGER, "
                + COLUMN_NB_BADGES + " INTEGER, "
                + COLUMN_XP + " INTEGER ) ";
        try {
            db.execSQL(createTableStatement);
        } catch (SQLiteException e) {
            try {
                throw new IOException(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS STATISTICS_TABLE");
        onCreate(db);
    }

    public boolean updateDB(Statistics mStatistics) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        System.out.println("helooooo");

        cv.put(COLUMN_DAILY_TIME, mStatistics.getDailyTime());
        cv.put(COLUMN_WEEKLY_TIME, mStatistics.getWeeklyTime());
        cv.put(COLUMN_DAY_STREAK, mStatistics.getDayStreak());
        cv.put(COLUMN_NB_BADGES, mStatistics.getNbBadges());
        cv.put(COLUMN_XP, mStatistics.getXP());

        long insert = db.insert(STATISTICS_TABLE, null, cv);
        return insert != -1;
    }
}
