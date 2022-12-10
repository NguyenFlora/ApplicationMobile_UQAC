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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class NewTaskActivity extends AppCompatActivity {

    DatePickerDialog picker;
    Spinner typesTache;
    EditText newtask_edittext_intitule;
    EditText newtask_edittext_description;
    TextView newtask_spinner_menuderoulant_text, eTextDate;
    ImageView close_button;
    Button new_task_button_confirm;
    DBHandler mDBHandler;
    Date date;
    String sDate;
    int userId;
    Task mTask;

    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_task);

        typesTache = (Spinner) findViewById(R.id.newtask_spinner_menuderoulant);
        newtask_edittext_intitule = (EditText) findViewById(R.id.newtask_edittext_intitule);
        newtask_edittext_description = (EditText) findViewById(R.id.newtask_edittext_description);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(NewTaskActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.types_tache));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typesTache.setAdapter(myAdapter);

        mDBHandler = new DBHandler(NewTaskActivity.this);
        userId = mDBHandler.getSharedPrefUserId(NewTaskActivity.this);
        newtask_spinner_menuderoulant_text = findViewById(R.id.newtask_spinner_menuderoulant_text);
        newtask_spinner_menuderoulant_text.setText("  " + getResources().getStringArray(R.array.types_tache)[0]);

        close_button = findViewById(R.id.close);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        new_task_button_confirm = findViewById(R.id.new_task_button_confirm);


        typesTache.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                type = typesTache.getSelectedItem().toString();
                newtask_spinner_menuderoulant_text.setText("  " + type);
                if (typesTache.getSelectedItem().toString().equals(getResources().getStringArray(R.array.types_tache)[0])){
                    for (Drawable drawable : newtask_spinner_menuderoulant_text.getCompoundDrawables()) {
                        if (drawable != null) {
                            drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(newtask_spinner_menuderoulant_text.getContext(), R.color.purple_500), PorterDuff.Mode.SRC_IN));
                        }
                    }
                }
                else {
                    for (Drawable drawable : newtask_spinner_menuderoulant_text.getCompoundDrawables()) {
                        if (drawable != null) {
                            drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(newtask_spinner_menuderoulant_text.getContext(), R.color.green1), PorterDuff.Mode.SRC_IN));
                        }
                    }                }
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        eTextDate=(TextView) findViewById(R.id.newtask_edittext_datelimite);
        eTextDate.setInputType(InputType.TYPE_NULL);
        eTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(NewTaskActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                sDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                eTextDate.setText(sDate);
                            }
                        }, year, month, day);
                picker.show();
                date = new GregorianCalendar(year, month - 1, day).getTime();
            }
        });

        new_task_button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDBHandler.addNewTask(type, newtask_edittext_intitule.getText().toString(), newtask_edittext_description.getText().toString(), date, userId);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}