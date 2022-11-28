package com.example.focusandstudy.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.focusandstudy.R;
import com.example.focusandstudy.model.User;
import com.example.focusandstudy.model.database.DBHandler;

public class FinishedTaskActivity extends AppCompatActivity {
    private Button m_end_the_session;
    private Button m_relaunch_the_session;

    DBHandler dbHandler;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_task);

        m_end_the_session = (Button) findViewById(R.id.end_the_session);
        m_relaunch_the_session = (Button) findViewById(R.id.relaunch_the_session);
    }

    @Override
    protected void onStart() {
        super.onStart();

        m_end_the_session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent finishedSessionActivity = new Intent(FinishedTaskActivity.this, FinishedSessionActivity.class);
                startActivity(finishedSessionActivity);
                finish();
            }
        });

        m_relaunch_the_session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pomodoroActivity = new Intent(FinishedTaskActivity.this, PomodoroActivity.class);
                startActivity(pomodoroActivity);
                finish();
            }
        });
    }
}