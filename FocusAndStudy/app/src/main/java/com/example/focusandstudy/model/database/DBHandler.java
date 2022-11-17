package com.example.focusandstudy.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

        import android.content.ContentValues;
import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteException;


import androidx.annotation.Nullable;

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

    public boolean updateUserCredentialsDB(User mUser){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME,mUser.getUsername());
        cv.put(COLUMN_EMAIL,mUser.getEmail());
        cv.put(COLUMN_PASSWORD, mUser.getPassword());
        long insert = db.insert(USER_TABLE, null, cv);
        return insert != -1;
    }

    public ArrayList<String> getMails()
    {
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            String[] vColumns ={COLUMN_EMAIL};
            ArrayList<String> allMails = new ArrayList<String>();
            int i =0;
            Cursor vCursor = db.query(USER_TABLE, vColumns, null, null, null, null, null, null);

            /*vCursor.moveToFirst();

            for(vCursor.moveToFirst(); !vCursor.isAfterLast(); vCursor.moveToNext()) {
                i++;
                // The Cursor is now set to the right position
                allMails.add(vCursor.getString(i));
            }*/
            if (vCursor.moveToFirst()) {
                allMails.add(vCursor.getString(i));
                do {
                    i++;
                    allMails.add(vCursor.getString(0));
                } while (vCursor.moveToNext());
            }
            System.out.println(allMails);
            return allMails;
        }catch(SQLiteException | IndexOutOfBoundsException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public String getPasswordForEmail(String email)
    {
        String password = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String vWhereClause = null;
        String[] vWhereArgs = {COLUMN_EMAIL+"="+email};
        String[] vColumns ={COLUMN_PASSWORD};
        Cursor vCursor = db.query(USER_TABLE, vColumns, null, null, null, null, null, null);
        vCursor.moveToFirst();

        password = vCursor.getString(0);

        return password;

    }

    public String getUsernameFromId(int user_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] vWhereArgs = {COLUMN_EMAIL+"="+user_id};
        String[] vColumns ={COLUMN_PASSWORD};
        Cursor vCursor = db.query(USER_TABLE, vColumns, null, null, null, null, null, null);
        vCursor.moveToFirst();
        String username = "";

        if (vCursor.moveToFirst()) {
            do {
                username = vCursor.getString(1);

            } while (vCursor.moveToNext());
        }


        vCursor.close();
        return username;
    }

}