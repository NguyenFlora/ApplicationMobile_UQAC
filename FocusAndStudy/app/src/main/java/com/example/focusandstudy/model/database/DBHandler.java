package com.example.focusandstudy.model.database;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;

        import android.content.ContentValues;
import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteException;


import androidx.annotation.Nullable;

import com.example.focusandstudy.model.Task;
import com.example.focusandstudy.model.User;

        import java.io.IOException;
        import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_USER_ID = "COLUMN_USER_ID";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_EMAIL= "COLUMN_EMAIL";
    public static final String COLUMN_PASSWORD= "COLUMN_PASSWORD";
    public static final String COLUMN_DAILY_TIME = " COLUMN_DAILY_TIME ";
    public static final String COLUMN_WEEKLY_TIME = " COLUMN_WEEKLY_TIME ";
    public static final String COLUMN_DAY_STREAK = " COLUMN_DAY_STREAK ";
    public static final String COLUMN_NB_BADGES = " COLUMN_NB_BADGES ";
    public static final String COLUMN_XP = " COLUMN_XP ";

    public static final String TASK_TABLE = "TASK_TABLE";
    public static final String COLUMN_TASK_ID = "COLUMN_TASK_ID";
    public static final String COLUMN_TYPE = "COLUMN_TYPE";
    public static final String COLUMN_NAME= "COLUMN_NAME";
    public static final String COLUMN_DESCRIPTION= "COLUMN_DESCRIPTION";
    public static final String COLUMN_DATE= "COLUMN_DATE";
    public static final String COLUMN_STATUS= "COLUMN_STATUS";

    private Context context;
    public DBHandler(@Nullable Context context) {
        super(context, "focusandstudy.db", null, 16);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTableStatement = "CREATE TABLE IF NOT EXISTS " + USER_TABLE + " ("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  "
                + COLUMN_USERNAME + " TEXT, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_PASSWORD + " TEXT, "
                + COLUMN_DAILY_TIME + " INTEGER, "
                + COLUMN_WEEKLY_TIME + " INTEGER, "
                + COLUMN_DAY_STREAK + " INTEGER, "
                + COLUMN_NB_BADGES + " INTEGER, "
                + COLUMN_XP + " INTEGER ) ";

        String createTaskTableStatement = "CREATE TABLE IF NOT EXISTS " + TASK_TABLE + "("
                + COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  "
                + COLUMN_TYPE + " TEXT, "
                + COLUMN_NAME + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_DATE + " DATE, "
                + COLUMN_STATUS + " TEXT,"
                + COLUMN_USER_ID + " INTEGER );";
        try {
            db.execSQL(createUserTableStatement);
            db.execSQL(createTaskTableStatement);

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
        db.execSQL("DROP TABLE IF EXISTS USER_TABLE");
        onCreate(db);
    }

    public String getSharedPrefUsername(Activity mActivity){
        SharedPreferences sharedPref = mActivity.getSharedPreferences("UserManagement", Context.MODE_PRIVATE);
        return sharedPref.getString("logged_username", "hello");
    }

    public int getSharedPrefUserId(Activity mActivity){
        SharedPreferences sharedPref = mActivity.getSharedPreferences("UserManagement", Context.MODE_PRIVATE);
        return sharedPref.getInt("logged_user_id", 1);
    }

    public boolean addNewUser(String username, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME,username);
        cv.put(COLUMN_EMAIL,email);
        cv.put(COLUMN_PASSWORD, password);
        long insert = db.insert(USER_TABLE, null, cv);
        db.close();
        return insert != -1;
    }

    public User login(String email, String password) {
        try{
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursorUsers = db.rawQuery(
                    "SELECT * FROM " + USER_TABLE + " WHERE " +
                            COLUMN_EMAIL + " = \'" + email + "\' AND " +
                            COLUMN_PASSWORD + " = \'" + password + "\'", null);

            ArrayList<User> userArrayList = new ArrayList<>();

            if (cursorUsers.moveToFirst()) {
                do {
                    userArrayList.add(new User(
                            cursorUsers.getInt(0),
                            cursorUsers.getString(1),
                            cursorUsers.getString(2),
                            cursorUsers.getString(3),
                            cursorUsers.getInt(4),
                            cursorUsers.getInt(5),
                            cursorUsers.getInt(6),
                            cursorUsers.getInt(7),
                            cursorUsers.getInt(8)));
                } while (cursorUsers.moveToNext());
            }
            cursorUsers.close();
            return userArrayList.get(0);
        }catch(SQLiteException | IndexOutOfBoundsException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public User getUserFromId(int user_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] vWhereArgs = {COLUMN_USER_ID+"="+user_id};
        String[] vColumns ={"*"};
        Cursor vCursor = db.query(USER_TABLE, vColumns, null, null, null, null, null, null);
        vCursor.moveToFirst();
        User user = new User();

        if (vCursor.moveToFirst()) {
            do {
                user.setId(vCursor.getInt(0));
                user.setUsername(vCursor.getString(1));
                user.setEmail(vCursor.getString(2));
                user.setPassword(vCursor.getString(3));
                user.setDailyTime(vCursor.getInt(4));
                user.setWeeklyTime(vCursor.getInt(5));
                user.setDayStreak(vCursor.getInt(6));
                user.setNbBadges(vCursor.getInt(7));
                user.setXP(vCursor.getInt(8));
            } while (vCursor.moveToNext());
        }
        vCursor.close();
        return user;
    }

    public boolean updateSessionStats(User mUser){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DAILY_TIME,mUser.getDailyTime() + 100);
        cv.put(COLUMN_WEEKLY_TIME,mUser.getWeeklyTime() + 100);
        cv.put(COLUMN_XP, mUser.getXP() + 15);
        String id = String.valueOf(mUser.getId());
        long insert = db.update(USER_TABLE, cv, COLUMN_USER_ID+" = ?", new String[]{id});
        db.close();
        return insert != -1;
    }

    public boolean addNewTask(Task mTask){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TYPE,mTask.getType());
        cv.put(COLUMN_NAME,mTask.getName());
        cv.put(COLUMN_DESCRIPTION, mTask.getDescription());
        cv.put(COLUMN_DATE,mTask.getDate().toString());
        cv.put(COLUMN_STATUS, mTask.getStatus());
        long insert = db.insert(TASK_TABLE, null, cv);
        db.close();
        return insert != -1;
    }

    public boolean updateTaskDone(int mTaskId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_STATUS,"FINISHED");
        String id = String.valueOf(mTaskId);
        long insert = db.update(TASK_TABLE, cv, COLUMN_TASK_ID+" = ?", new String[]{id});
        db.close();
        return insert != -1;
    }

    public boolean updateNewWeek(int userId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_WEEKLY_TIME, 0);
        String id = String.valueOf(userId);
        long insert = db.update(USER_TABLE, cv, COLUMN_USER_ID+" = ?", new String[]{id});
        db.close();
        return insert != -1;
    }

    public boolean updateNewDay(int userId, int dayStreak){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DAILY_TIME, 0);
        cv.put(COLUMN_DAY_STREAK, dayStreak);
        String id = String.valueOf(userId);
        long insert = db.update(USER_TABLE, cv, COLUMN_USER_ID+" = ?", new String[]{id});
        db.close();
        return insert != -1;
    }

}