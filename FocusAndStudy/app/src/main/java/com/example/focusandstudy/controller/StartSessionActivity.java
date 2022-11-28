package com.example.focusandstudy.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.focusandstudy.R;
import com.example.focusandstudy.StartSessionItemFragment;
import com.example.focusandstudy.databinding.FragmentStartSessionItemBinding;
import com.example.focusandstudy.model.User;
import com.example.focusandstudy.model.database.DBHandler;
import com.example.focusandstudy.ui.calendar.CalendarMonthlyItemFragment;

import java.util.List;

public class StartSessionActivity extends AppCompatActivity {

    LinearLayout task_list;

    DBHandler dbHandler;
    User currentUser;
    FragmentStartSessionItemBinding fragmentStartSessionItemBinding;
    Button startTheSession;
    ImageView back;
    List<Fragment> fragments;
    String currentTask;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_start_session);

        task_list = (LinearLayout) findViewById(R.id.task_list);

        dbHandler = new DBHandler(StartSessionActivity.this);

        startTheSession = (Button) findViewById(R.id.start_the_session);
        back = (ImageView) findViewById(R.id.back);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        for(int i = 0; i < 10; i++){
            Fragment newFragment = new StartSessionItemFragment(i);
            ft.add(R.id.task_list, newFragment);
        }

        ft.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FragmentManager fm = getSupportFragmentManager();
        fragments = fm.getFragments();
        StartSessionItemFragment currentFragment = (StartSessionItemFragment) fragments.get(0);
        currentTask = currentFragment.getTask_text().getText().toString();
        id = 0;

        for(int i = 0; i < 10; i++){
            StartSessionItemFragment fragment = (StartSessionItemFragment) fragments.get(i);
            int finalI = i;
            fragment.getTask().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    id = finalI;
                    fragment.setClicked(true);
                    fragment.getTask().setBackgroundResource(R.drawable.box);
                    fragment.getTask_text().setTextColor(getResources().getColor(R.color.green2));
                    Typeface typeface = ResourcesCompat.getFont(fragment.getContext(), R.font.montserrat_medium);
                    fragment.getTask_text().setTypeface(typeface);
                    fragment.getTask_date().setTextColor(getResources().getColor(R.color.gray));
                    currentTask = fragment.getTask_text().getText().toString();
                    for(int j = 0; j < 10; j++){
                        if (j!= finalI){
                            fragment.setClicked(false);
                            StartSessionItemFragment unclicked_fragment = (StartSessionItemFragment) fragments.get(j);
                            unclicked_fragment.getTask().setBackgroundResource(R.drawable.box4);
                            unclicked_fragment.getTask_text().setTextColor(getResources().getColor(R.color.green1));
                            Typeface typeface2 = ResourcesCompat.getFont(unclicked_fragment.getContext(), R.font.montserrat);
                            unclicked_fragment.getTask_text().setTypeface(typeface2);
                            unclicked_fragment.getTask_date().setTextColor(getResources().getColor(R.color.gray6));

                        }
                    }
                }
            });
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        startTheSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = getSharedPreferences("CurrentTask", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("name", currentTask);
                editor.putInt("id", id);
                editor.apply();

                Intent pomodoroActivity = new Intent(StartSessionActivity.this, PomodoroActivity.class);
                startActivity(pomodoroActivity);
                finish();
            }
        });

    }
}