package com.example.focusandstudy.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.focusandstudy.R;
import com.example.focusandstudy.model.User;
import com.example.focusandstudy.model.database.DBHandler;

public class PomodoroActivity extends AppCompatActivity {
    private ImageView m_pomodoro_image_dropblue1;
    private ImageView m_pomodoro_image_dropwhite1;
    private ImageView m_pomodoro_image_dropblue2;
    private ImageView m_pomodoro_image_dropwhite2;
    private ImageView m_pomodoro_image_dropblue3;
    private ImageView m_pomodoro_image_dropwhite3;
    private ImageView m_pomodoro_image_dropblue4;
    private ImageView m_pomodoro_image_dropwhite4;
    private ImageView m_pomodoro_image_tree1;
    private ImageView m_pomodoro_image_tree2;
    private ImageView m_pomodoro_image_tree3;
    private ImageView m_pomodoro_image_tree4;
    private ImageView m_pomodoro_image_treebreak;
    private ProgressBar m_pomodoro_progressbar_time;
    private TextView m_pomodoro_text_time;
    private Button m_pomodoro_button_break;
    private TextView m_pomodoro_text_break;
    private Button m_pomodoro_button_music;
    private Button m_pomodoro_button_exit;
    CountDownTimer timer;
    long milliLeft;
    long fullTime;
    String typeCounter;
    boolean onBreak = false;
    boolean isPaused;
    int i = 0;
    DBHandler dbHandler;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);

        m_pomodoro_image_dropblue1 = (ImageView) findViewById(R.id.pomodoro_image_dropblue1);
        m_pomodoro_image_dropwhite1 = (ImageView) findViewById(R.id.pomodoro_image_dropwhite1);
        m_pomodoro_image_dropblue2 = (ImageView) findViewById(R.id.pomodoro_image_dropblue2);
        m_pomodoro_image_dropwhite2 = (ImageView) findViewById(R.id.pomodoro_image_dropwhite2);
        m_pomodoro_image_dropblue3 = (ImageView) findViewById(R.id.pomodoro_image_dropblue3);
        m_pomodoro_image_dropwhite3 = (ImageView) findViewById(R.id.pomodoro_image_dropwhite3);
        m_pomodoro_image_dropblue4 = (ImageView) findViewById(R.id.pomodoro_image_dropblue4);
        m_pomodoro_image_dropwhite4 = (ImageView) findViewById(R.id.pomodoro_image_dropwhite4);
        m_pomodoro_image_tree1 = (ImageView) findViewById(R.id.pomodoro_image_tree1);
        m_pomodoro_image_tree2 = (ImageView) findViewById(R.id.pomodoro_image_tree2);
        m_pomodoro_image_tree3 = (ImageView) findViewById(R.id.pomodoro_image_tree3);
        m_pomodoro_image_tree4 = (ImageView) findViewById(R.id.pomodoro_image_tree4);
        m_pomodoro_image_treebreak = (ImageView) findViewById(R.id.pomodoro_image_treebreak);
        m_pomodoro_progressbar_time = (ProgressBar) findViewById(R.id.pomodoro_progressbar_time);
        m_pomodoro_text_time = (TextView) findViewById(R.id.pomodoro_text_time);
        m_pomodoro_button_break = (Button) findViewById(R.id.pomodoro_button_break);
        m_pomodoro_text_break = (TextView) findViewById(R.id.pomodoro_text_break);
        m_pomodoro_button_music = (Button) findViewById(R.id.pomodoro_button_music);
        m_pomodoro_button_exit = (Button) findViewById(R.id.pomodoro_button_exit);

        isPaused = false;
        dbHandler = new DBHandler(PomodoroActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
            newCycle();

        m_pomodoro_button_break.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPaused) {
                    timerPause();
                    isPaused = true;
                }
                else {
                    timerResume();
                    isPaused = false;
                }
            }
        });

        m_pomodoro_button_music.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        m_pomodoro_button_exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
    }

    public void newCycle(){
        timerStart(15000,15000, "work"); //25min => 1500000
    }

    public void cycleBreak(int i){
        if(i==3) {
            Intent finishedSessionActivity = new Intent(PomodoroActivity.this, FinishedSessionActivity.class);
            startActivity(finishedSessionActivity);
            finish();
        }
        else timerStart(15000,15000, "break"); //10min => 300000
    }

    public void timerStart(long timeLengthMilli, long fullTimeMilli, String counterReason){
        System.out.println("timer on " + counterReason);
        timer = new CountDownTimer(timeLengthMilli, 1000) {
            @Override
            public void onTick(long milliTillFinish) {
                fullTime = fullTimeMilli;
                milliLeft = milliTillFinish;
                typeCounter = counterReason;
                m_pomodoro_text_time.setText(String.valueOf(DateUtils.formatElapsedTime(milliTillFinish/1000)));
                m_pomodoro_progressbar_time.setMax((int)fullTime/1000);

                if(milliTillFinish/1000 >0) {
                    m_pomodoro_progressbar_time.setProgress((int) (milliLeft / 1000));
                }
                if(milliTillFinish/1000 == 0) {
                    m_pomodoro_progressbar_time.setProgress(0);
                }
                if(counterReason.equals("work")) {
                    m_pomodoro_image_tree1.setVisibility(View.VISIBLE);
                    m_pomodoro_image_treebreak.setVisibility(View.INVISIBLE);
                    m_pomodoro_text_break.setVisibility(View.INVISIBLE);

                    if(milliTillFinish<(fullTime*0.75) && milliTillFinish>(fullTime*0.5)) {
                        m_pomodoro_image_tree1.setVisibility(View.INVISIBLE);
                        m_pomodoro_image_tree2.setVisibility(View.VISIBLE);
                    }
                    if(milliTillFinish<(fullTime*0.5) && milliTillFinish>(fullTime*0.25)) {
                        m_pomodoro_image_tree2.setVisibility(View.INVISIBLE);
                        m_pomodoro_image_tree3.setVisibility(View.VISIBLE);
                    }
                    if(milliTillFinish<(fullTime*0.25) && milliTillFinish>0) {
                        m_pomodoro_image_tree3.setVisibility(View.INVISIBLE);
                        m_pomodoro_image_tree4.setVisibility(View.VISIBLE);
                    }
                }
                if(counterReason.equals("break")) {
                    m_pomodoro_image_tree4.setVisibility(View.INVISIBLE);
                    m_pomodoro_image_treebreak.setVisibility(View.VISIBLE);
                    m_pomodoro_text_break.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFinish() {
                System.out.println("timer end");

                if(onBreak){
                    newCycle();
                    onBreak = false;
                    System.out.println("nouveau cycle");
                    i++;
                    System.out.println("i : " + i);
                    switch(i) {
                        case 1:
                            m_pomodoro_image_dropblue2.setVisibility(View.VISIBLE);
                            m_pomodoro_image_dropwhite2.setVisibility(View.INVISIBLE);
                            break;
                        case 2:
                            m_pomodoro_image_dropblue3.setVisibility(View.VISIBLE);
                            m_pomodoro_image_dropwhite3.setVisibility(View.INVISIBLE);
                            break;
                        case 3:
                            m_pomodoro_image_dropblue4.setVisibility(View.VISIBLE);
                            m_pomodoro_image_dropwhite4.setVisibility(View.INVISIBLE);
                            break;
                    }
                }
                else {
                    cycleBreak(i);
                    onBreak = true;
                    System.out.println("en pause");
                }

            }
        }.start();
    }
    public void timerPause() {
        timer.cancel();
        System.out.println("timer paused");
    }

    private void timerResume() {
        timerStart(milliLeft, fullTime, typeCounter);
        System.out.println("timer restart");
    }
}