package com.example.focusandstudy.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.focusandstudy.model.Task;

import java.io.IOException;

public class TaskDB extends SQLiteOpenHelper {
    public static final String TASK_TABLE = "TASK_TABLE";
    public static final String COLUMN_TASK_ID = "COLUMN_TASK_ID";
    public static final String COLUMN_TYPE = "COLUMN_TYPE";
    public static final String COLUMN_NAME= "COLUMN_NAME";
    public static final String COLUMN_DESCRIPTION= "COLUMN_DESCRIPTION";
    public static final String COLUMN_DATE= "COLUMN_DATE";
    public static final String COLUMN_STATUS= "COLUMN_STATUS";
    public static final String COLUMN_USER = "COLUMN_USER";
    public static final String USER_TABLE= "USER_TABLE";
    public static final String COLUMN_USER_ID = "COLUMN_USER_ID";
    private Context context;
    public TaskDB(@Nullable Context context) {
        super(context, "focusandstudy.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + TASK_TABLE + "("
                + COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  "
                + COLUMN_TYPE + " TEXT, "
                + COLUMN_NAME + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_DATE + " DATE, "
                + COLUMN_STATUS + " TEXT,"
                + " FOREIGN KEY ("+COLUMN_USER+") REFERENCES "+USER_TABLE+"("+COLUMN_USER_ID+"));";
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
        db.execSQL("DROP TABLE IF EXISTS TASK_TABLE");
        onCreate(db);
    }

    public boolean updateDB(Task mTask){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();



        long insert = db.insert(TASK_TABLE, null, cv);
        return insert != -1;
    }
}
