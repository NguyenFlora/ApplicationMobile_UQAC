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
import com.example.focusandstudy.ui.calendar.CalendarMonthlyFragment;

import java.util.Calendar;

public class NewTaskActivity extends AppCompatActivity {

    DatePickerDialog picker;
    TextView newtask_spinner_menuderoulant_text, eText;
    ImageView close_button;
    Button changemusicdialog_button_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_task);

        Spinner typesTache = (Spinner) findViewById(R.id.newtask_spinner_menuderoulant);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(NewTaskActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.types_tache));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typesTache.setAdapter(myAdapter);

        newtask_spinner_menuderoulant_text = findViewById(R.id.newtask_spinner_menuderoulant_text);
        newtask_spinner_menuderoulant_text.setText("  " + getResources().getStringArray(R.array.types_tache)[0]);

        close_button = findViewById(R.id.close);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        changemusicdialog_button_confirm = findViewById(R.id.chnagemusicdialog_button_confirm);
        changemusicdialog_button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newTaskActivity = new Intent(NewTaskActivity.this, CalendarMonthlyFragment.class);
                startActivity(newTaskActivity);            }
        });

        typesTache.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                newtask_spinner_menuderoulant_text.setText("  " + typesTache.getSelectedItem().toString());
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

        eText=(TextView) findViewById(R.id.newtask_edittext_datelimite);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
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
                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}