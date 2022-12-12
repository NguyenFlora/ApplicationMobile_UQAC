package com.example.focusandstudy.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button m_pomodoro_button_end_break_time;
    CountDownTimer timer;
    long milliLeft;
    long fullTime;
    String typeCounter;
    boolean onBreak = false;
    boolean isPaused;
    int i = 0;
    DBHandler mDBHandler;
    int user_id;
    User currentUser;
    public static PomodoroActivity instance = null;
    Context context = this;
    RadioButton radioButton;
    MediaPlayer mediaPlayer;
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
        m_pomodoro_button_end_break_time = (Button) findViewById(R.id.pomodoro_button_end_break_time);

        isPaused = false;
        mDBHandler = new DBHandler(PomodoroActivity.this);
        user_id =  mDBHandler.getSharedPrefUserId(PomodoroActivity.this);

        m_pomodoro_button_exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showCustomDialogExit();
            }
        });

        m_pomodoro_button_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialogMusic();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
            newCycle();
        instance = this;
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

        m_pomodoro_button_end_break_time.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                timer.cancel();
                newCycle();
            }
        });
    }

    public void newCycle(){
        timerStart(15000,15000, "work"); //25min => 1500000
        onBreak = false;
        System.out.println("nouveau cycle");
        i++;
        System.out.println("i : " + i);
        m_pomodoro_image_tree1.setVisibility(View.VISIBLE);
        m_pomodoro_image_treebreak.setVisibility(View.INVISIBLE);
        m_pomodoro_text_break.setVisibility(View.INVISIBLE);
        m_pomodoro_button_end_break_time.setVisibility(View.INVISIBLE);
    }

    public void cycleBreak(int i){
        if(i==1) {
            currentUser = mDBHandler.getUserFromId(user_id);
            mDBHandler.updateSessionStats(currentUser);

            Intent finishedTaskActivity = new Intent(PomodoroActivity.this, FinishedTaskActivity.class);
            startActivity(finishedTaskActivity);
            System.out.println("pomodoro finished");
            stop();
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
                    stop();
                    m_pomodoro_image_tree4.setVisibility(View.INVISIBLE);
                    m_pomodoro_image_treebreak.setVisibility(View.VISIBLE);
                    m_pomodoro_text_break.setVisibility(View.VISIBLE);
                    m_pomodoro_button_end_break_time.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFinish() {
                System.out.println("timer end");
                if(onBreak){
                    newCycle();

                }
                else {
                    switch(i) {
                        case 1:
                            m_pomodoro_image_dropblue1.setVisibility(View.VISIBLE);
                            m_pomodoro_image_dropwhite1.setVisibility(View.INVISIBLE);
                            break;
                        case 2:
                            m_pomodoro_image_dropblue2.setVisibility(View.VISIBLE);
                            m_pomodoro_image_dropwhite2.setVisibility(View.INVISIBLE);
                            break;
                        case 3:
                            m_pomodoro_image_dropblue3.setVisibility(View.VISIBLE);
                            m_pomodoro_image_dropwhite3.setVisibility(View.INVISIBLE);
                            break;
                        case 4:
                            m_pomodoro_image_dropblue4.setVisibility(View.VISIBLE);
                            m_pomodoro_image_dropwhite4.setVisibility(View.INVISIBLE);
                            break;
                    }
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


    void showCustomDialogExit() {
        final Dialog dialog = new Dialog(PomodoroActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.exit_session_dialog);
        dialog.show();

        Button undo = dialog.findViewById(R.id.exitsessiondialog_button_cancel);
        Button exit = dialog.findViewById(R.id.exitsessiondialog_button_exit);

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                timer.cancel();
                timer = null;
                finish();
            }
        });

    }

    void showCustomDialogMusic(){
        final Dialog dialog = new Dialog(PomodoroActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.change_music_dialog);
        dialog.show();

        Button confirm = (Button) dialog.findViewById(R.id.changemusicdialog_button_confirm);
        RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.changemusicdialog_radiogroup);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedMusic = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) dialog.findViewById(selectedMusic);
                String musicValue = radioButton.getTag().toString();
                playMusic(musicValue);
                Toast.makeText(context,radioButton.getText(),Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
    private void playMusic(String title) {
        stop();
        float volume = (float) (1 - (Math.log(50) / Math.log(100)));
        int rawSoundId = 0;
        switch(title){
            case "none":
                stop();
                break;
            case "whitenoise":
                volume = (float) (1 - (Math.log(20) / Math.log(100)));
                rawSoundId = R.raw.whitenoise;
                break;
            case "water":
                rawSoundId = R.raw.water;
                break;
            case "rain":
                rawSoundId = R.raw.rain;
                break;
            case "waves":
                rawSoundId = R.raw.waves;
                break;
        }
        mediaPlayer = MediaPlayer.create(PomodoroActivity.this, rawSoundId);
        mediaPlayer.setVolume(volume, volume);
        mediaPlayer.start();
    }

    public void stop() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


}