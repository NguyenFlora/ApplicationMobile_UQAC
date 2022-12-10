package com.example.focusandstudy.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.focusandstudy.R;
import com.example.focusandstudy.model.User;
import com.example.focusandstudy.model.database.DBHandler;

public class FinishedSessionActivity extends AppCompatActivity {
    private Button m_finished_session_yes;
    private Button m_finished_session_no;

    DBHandler dbHandler;
    int task_id;
    String task_name;
    TextView finished_session_task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_session);

        m_finished_session_yes = (Button) findViewById(R.id.finished_session_yes);
        m_finished_session_no = (Button) findViewById(R.id.finished_session_no);
        finished_session_task = (TextView) findViewById(R.id.finished_session_task);
        dbHandler = new DBHandler(FinishedSessionActivity.this);
        dbHandler.getSharedPrefTaskName(FinishedSessionActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        task_id =  dbHandler.getSharedPrefTaskId(FinishedSessionActivity.this);
        task_name = dbHandler.getSharedPrefTaskName(FinishedSessionActivity.this);
        finished_session_task.setText(task_name);
        m_finished_session_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("task finished");
                dbHandler.updateTaskDone(task_id);
                goToMainActivity();
            }
        });

        m_finished_session_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("task not finished");
                goToMainActivity();
            }
        });
    }

    private void goToMainActivity() {
        Intent mainActivity = new Intent(FinishedSessionActivity.this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }
}