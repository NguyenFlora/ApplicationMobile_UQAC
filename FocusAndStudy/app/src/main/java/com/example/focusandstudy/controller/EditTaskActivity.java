package com.example.focusandstudy.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.focusandstudy.R;
import com.example.focusandstudy.model.Task;
import com.example.focusandstudy.model.database.DBHandler;
import com.example.focusandstudy.ui.calendar.CalendarMonthlyFragment;

import java.util.Calendar;
import java.util.Date;

public class EditTaskActivity extends AppCompatActivity {

    DatePickerDialog picker;
    Spinner edit_task_type_spinner;
    EditText edit_task_entitled_input;
    EditText edit_task_description_input;
    TextView edit_task_type_spinner_text, eTextDate;
    ImageView close_button;
    Button edit_task_button_confirm;
    DBHandler mDBHandler;
    String sDate;
    int taskId;
    Task task;

    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        mDBHandler = new DBHandler(EditTaskActivity.this);

        edit_task_type_spinner = (Spinner) findViewById(R.id.edit_task_type_spinner);
        edit_task_entitled_input = (EditText) findViewById(R.id.edit_task_entitled_input);
        edit_task_description_input = (EditText) findViewById(R.id.edit_task_description_input);
        eTextDate=(TextView) findViewById(R.id.edit_task_date_input);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(EditTaskActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.types_tache));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edit_task_type_spinner.setAdapter(myAdapter);

        taskId = mDBHandler.getSharedPrefTaskId(EditTaskActivity.this);
        task = mDBHandler.getTaskFromId(taskId);

        if (task.getType().equals("Examen"))
            edit_task_type_spinner.setSelection(0);
        else
            edit_task_type_spinner.setSelection(1);


        edit_task_type_spinner_text = findViewById(R.id.edit_task_type_spinner_text);
        edit_task_entitled_input.setText(task.getName());
        edit_task_description_input.setText(task.getDescription());
        eTextDate.setText(task.getDate());

        close_button = findViewById(R.id.close);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        edit_task_button_confirm = findViewById(R.id.edit_task_button_confirm);


        edit_task_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                type = edit_task_type_spinner.getSelectedItem().toString();
                edit_task_type_spinner_text.setText("  " + type);
                if (edit_task_type_spinner.getSelectedItem().toString().equals(getResources().getStringArray(R.array.types_tache)[0])){
                    for (Drawable drawable : edit_task_type_spinner_text.getCompoundDrawables()) {
                        if (drawable != null) {
                            drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(edit_task_type_spinner_text.getContext(), R.color.purple_500), PorterDuff.Mode.SRC_IN));
                        }
                    }
                }
                else {
                    for (Drawable drawable : edit_task_type_spinner_text.getCompoundDrawables()) {
                        if (drawable != null) {
                            drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(edit_task_type_spinner_text.getContext(), R.color.green1), PorterDuff.Mode.SRC_IN));
                        }
                    }                }
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        eTextDate.setInputType(InputType.TYPE_NULL);
        eTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(EditTaskActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                sDate =  year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                eTextDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
                //date = new GregorianCalendar(year, month - 1, day).getTime();
            }
        });

        edit_task_button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task.setType(edit_task_type_spinner_text.getText().toString());
                task.setName(edit_task_entitled_input.getText().toString());
                task.setDate(sDate);
                task.setDescription(edit_task_description_input.getText().toString());
                mDBHandler.updateTask(task);
                Intent taskDetailsActivity = new Intent(EditTaskActivity.this, TaskDetailsActivity.class);
                startActivity(taskDetailsActivity);
                finish();
            }
        });
    }


        @Override
    protected void onStart() {
        super.onStart();
    }
}

