package com.example.focusandstudy.model.database;/*package com.example.focusandstudy.model.database;
package com.example.focusandstudy.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

//mport net.zetetic.database.sqlcipher.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.focusandstudy.model.User;

import java.io.IOException;
import java.util.ArrayList;

public class UserDB extends SQLiteOpenHelper {
    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_USER_ID = "COLUMN_USER_ID";
    public static final String COLUMN_USERNAME = "COLUMN_USERNAME";
    public static final String COLUMN_EMAIL= "COLUMN_EMAIL";
    public static final String COLUMN_PASSWORD= "COLUMN_PASSWORD";
    public static final String COLUMN_STATISTICS= "COLUMN_STATISTICS";
    public static final String STATISTICS_TABLE= "STATISTICS_TABLE";
    public static final String COLUMN_STATISTICS_ID = "COLUMN_STATISTICS_ID";
    private Context context;
    public UserDB(@Nullable Context context) {
        super(context, "focusand.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + USER_TABLE + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  "
                + COLUMN_USERNAME + " TEXT, "
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PASSWORD + " TEXT"+
                //+ " FOREIGN KEY ("+COLUMN_STATISTICS+") REFERENCES "+STATISTICS_TABLE+"("+COLUMN_STATISTICS_ID+")" +
                ")";
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
        db.execSQL("DROP TABLE IF EXISTS USER_TABLE");
        onCreate(db);
    }

    public boolean updateUserCredentialsDB(String username,String email,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME,username);
        cv.put(COLUMN_EMAIL,email);
        cv.put(COLUMN_PASSWORD, password);
        long insert = db.insert(USER_TABLE, null, cv);
        return insert != -1;
    }

    public ArrayList<String> getMails()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String vWhereClause = null;
        String[] vWhereArgs = null;
        String[] vColumns ={COLUMN_EMAIL};
        Cursor vCursor = db.query(USER_TABLE, vColumns, null, null, null, null, null, null);
        vCursor.moveToFirst();

        ArrayList<String> allMails = new ArrayList<String>();
        int i =-1;
        for(vCursor.moveToFirst(); !vCursor.isAfterLast(); vCursor.moveToNext()) {
            i++;
            // The Cursor is now set to the right position
            allMails.add(vCursor.getString(i));
        }
        return allMails;
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

}
*/

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteException;
        import android.database.sqlite.SQLiteOpenHelper;


        import androidx.annotation.Nullable;

        import com.example.focusandstudy.model.User;

        import java.io.IOException;
        import java.util.ArrayList;

public class UserDB extends SQLiteOpenHelper {
    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_USER_ID = "COLUMN_USER_ID";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_EMAIL= "COLUMN_EMAIL";
    public static final String COLUMN_PASSWORD= "COLUMN_PASSWORD";
    public static final String COLUMN_STATISTICS= "COLUMN_STATISTICS";
    public static final String STATISTICS_TABLE= "STATISTICS_TABLE";
    public static final String COLUMN_STATISTICS_ID = "COLUMN_STATISTICS_ID";
    private Context context;
    public UserDB(@Nullable Context context) {
        super(context, "focusandstudy.db", null, 16);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + USER_TABLE + " ("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  "
                + COLUMN_USERNAME + " TEXT, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_PASSWORD + " TEXT, "
                + COLUMN_STATISTICS + " INTEGER, "
                + " FOREIGN KEY ("+COLUMN_STATISTICS+") REFERENCES "+STATISTICS_TABLE+"("+COLUMN_STATISTICS_ID+")" +
                ");";
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
        db.execSQL("DROP TABLE IF EXISTS USER_TABLE");
        onCreate(db);
    }

    public boolean updateUserCredentialsDB(User mUser){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME,mUser.getUsername());
        cv.put(COLUMN_EMAIL,mUser.getEmail());
        cv.put(COLUMN_PASSWORD, mUser.getPassword());
        System.out.println(mUser.getStatistics().getId());
        System.out.println(mUser.getStatistics().getNextId());
        cv.put(COLUMN_STATISTICS, mUser.getStatistics().getId());
        long insert = db.insert(USER_TABLE, null, cv);
        return insert != -1;
    }

    public ArrayList<String> getMails()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String vWhereClause = null;
        String[] vWhereArgs = null;
        String[] vColumns ={COLUMN_EMAIL};
        Cursor vCursor = db.query(USER_TABLE, vColumns, null, null, null, null, null, null);
        vCursor.moveToFirst();

        ArrayList<String> allMails = new ArrayList<String>();
        int i =-1;
        for(vCursor.moveToFirst(); !vCursor.isAfterLast(); vCursor.moveToNext()) {
            i++;
            // The Cursor is now set to the right position
            allMails.add(vCursor.getString(i));
        }
        return allMails;
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

}