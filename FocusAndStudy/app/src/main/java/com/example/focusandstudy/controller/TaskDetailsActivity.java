package com.example.focusandstudy.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.focusandstudy.R;
import com.example.focusandstudy.model.Task;
import com.example.focusandstudy.model.User;
import com.example.focusandstudy.model.database.DBHandler;

public class TaskDetailsActivity extends AppCompatActivity {

    Button taskdetail_button_lancersession, taskdetail_button_retour, taskdetail_button_modifier, taskdetail_button_effacer ;
    TextView taskdetail_text_titretache, taskdetail_text_typetache, date, taskdetail_text_nbsession, taskdetail_text_decription, taskdetail_text_state;
    ImageView taskdetail_image_typetache;
    CheckBox taskdetail_checkbox;
    DBHandler mDBHandler;
    Task task;
    int taskId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        mDBHandler = new DBHandler(TaskDetailsActivity.this);
        taskId = mDBHandler.getSharedPrefTaskId(TaskDetailsActivity.this);
        task = mDBHandler.getTaskFromId(taskId);
        taskdetail_button_lancersession = findViewById(R.id.taskdetail_button_lancersession);
        taskdetail_button_effacer = findViewById(R.id.taskdetail_button_effacer);
        taskdetail_button_modifier = findViewById(R.id.taskdetail_button_modifier);
        taskdetail_button_lancersession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pomodoroActivity = new Intent(TaskDetailsActivity.this, PomodoroActivity.class);
                startActivity(pomodoroActivity);
                finish();
            }
        });

        taskdetail_button_retour = findViewById(R.id.taskdetail_button_retour);
        taskdetail_button_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        taskdetail_text_titretache = findViewById(R.id.taskdetail_text_titretache);
        taskdetail_text_typetache = findViewById(R.id.taskdetail_text_typetache);
        date = findViewById(R.id.date);
        taskdetail_text_nbsession = findViewById(R.id.taskdetail_text_nbsession);
        taskdetail_text_decription = findViewById(R.id.taskdetail_text_decription);
        taskdetail_image_typetache = findViewById(R.id.taskdetail_image_typetache);
        taskdetail_checkbox = findViewById(R.id.taskdetail_checkbox);
        taskdetail_text_state = findViewById(R.id.taskdetail_text_state);


        taskdetail_text_titretache.setText(task.getName());
        taskdetail_text_typetache.setText(task.getType());
        date.setText(task.getDate());
        taskdetail_text_nbsession.setText("0");
        taskdetail_text_decription.setText(task.getDescription());

        taskdetail_image_typetache.setColorFilter(R.color.green1,android.graphics.PorterDuff.Mode.MULTIPLY);
        taskdetail_checkbox.setChecked(true);
        taskdetail_text_state.setText(R.string.task_over);

        taskdetail_button_effacer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDBHandler.deleteTask(taskId);
                finish();
            }
        });

        taskdetail_button_modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}