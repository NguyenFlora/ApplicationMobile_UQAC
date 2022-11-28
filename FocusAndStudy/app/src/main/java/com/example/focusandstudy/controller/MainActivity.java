package com.example.focusandstudy.controller;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;

import com.example.focusandstudy.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.focusandstudy.databinding.ActivityMainBinding;
import com.example.focusandstudy.model.User;
import com.example.focusandstudy.model.database.DBHandler;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DBHandler mDBHandler;
    private int user_id;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDBHandler = new DBHandler(MainActivity.this);
        user_id =  mDBHandler.getSharedPrefUserId(MainActivity.this);
        mUser = mDBHandler.getUserFromId(user_id);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(binding.getRoot());
        dayManager();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void dayManager() {
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        mDBHandler.updateDayTime(user_id, currentDay, mUser.getDailyTime());
        if(!checkIfDayExists(MainActivity.this)){
            addDayToSharedPreferences(currentDay, 1);
        }
        else {
            int sharedPrefDay = getDayFromSharedPreferences(MainActivity.this);
            int currentStreak = getStreakFromSharedPreferences(MainActivity.this)+1;
            System.out.println("shred"+sharedPrefDay);
            if (sharedPrefDay == Calendar.SUNDAY && currentDay == Calendar.MONDAY) {
                mDBHandler.updateNewWeek(user_id);
                mDBHandler.updateNewDay(user_id, currentStreak);
                addDayToSharedPreferences(currentDay, currentStreak);
            }

            else if (sharedPrefDay != currentDay) {
                mDBHandler.updateNewDay(user_id, currentStreak);
                addDayToSharedPreferences(currentDay, currentStreak);
            }
        }
    }

    public void addDayToSharedPreferences(int day, int streak){
        SharedPreferences sharedPref = getSharedPreferences("DayManagement", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("day", day);
        editor.putInt("dayStreak", streak);
        editor.apply();
    }

    public int getDayFromSharedPreferences(Activity mActivity){
        SharedPreferences sharedPref = mActivity.getSharedPreferences("DayManagement", Context.MODE_PRIVATE);
        return sharedPref.getInt("day", 0);
    }

    public int getStreakFromSharedPreferences(Activity mActivity){
        SharedPreferences sharedPref = mActivity.getSharedPreferences("DayManagement", Context.MODE_PRIVATE);
        return sharedPref.getInt("streak", 0);
    }

    public boolean checkIfDayExists(Activity mActivity){
        SharedPreferences sharedPref = mActivity.getSharedPreferences("DayManagement", Context.MODE_PRIVATE);
        return sharedPref.contains("day");
    }


}