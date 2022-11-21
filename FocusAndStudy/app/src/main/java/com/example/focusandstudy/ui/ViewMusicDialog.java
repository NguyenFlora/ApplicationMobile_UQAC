package com.example.focusandstudy.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.focusandstudy.R;

import java.io.IOException;
import java.util.Arrays;

public class ViewMusicDialog extends Dialog {

    MediaPlayer mediaPlayer;
    private RadioGroup m_changemusicdialog_radiogroup;
    private Button m_changemusicdialog_button_confirm;

    public ViewMusicDialog(@NonNull Context context) {
        super(context);
    }

    public ViewMusicDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ViewMusicDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.change_music_dialog);
        m_changemusicdialog_radiogroup = (RadioGroup) findViewById(R.id.changemusicdialog_radiogroup);
        m_changemusicdialog_button_confirm = (Button) findViewById(R.id.changemusicdialog_button_confirm);

    }

    @Override
    protected void onStart() {
        super.onStart();
        m_changemusicdialog_button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedBtnId = m_changemusicdialog_radiogroup.getCheckedRadioButtonId();
                Button btn = (Button) findViewById(checkedBtnId);
                String musicValue = btn.getTag().toString();
                playMusic(musicValue);
                dismiss();
                System.out.println(musicValue);
            }
        });
    }

    private void playMusic(String title) {
        final float volume = (float) (1 - (Math.log(70) / Math.log(100)));
        switch(title){
            case "none":
                stop();
            break;
            case "whitenoise":
                play(R.raw.whitenoise);
            break;
            case "water":
                play(R.raw.water);
            break;
            case "rain":
                play(R.raw.rain);
            break;
            case "waves":
                play(R.raw.waves);
            break;
        }
    }

    private void stopMusic(){
        System.out.println("bbbbbbbbbbbb");
        System.out.println(mediaPlayer);
        //if(mediaPlayer !=null){
            //System.out.println("aaaaaaaaaaaaaaa"+Arrays.toString(mediaPlayer.getTrackInfo()));

            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        //}
    }
    public void play(int rawSoundId){
        stop();
        mediaPlayer = MediaPlayer.create(getContext(), rawSoundId);
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
