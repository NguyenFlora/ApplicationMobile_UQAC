package com.example.focusandstudy.ui.calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.focusandstudy.R;
import com.example.focusandstudy.controller.NewTaskActivity;
import com.example.focusandstudy.controller.StartSessionActivity;
import com.example.focusandstudy.databinding.FragmentCalendarMonthlyBinding;
import com.example.focusandstudy.model.Task;
import com.example.focusandstudy.model.User;
import com.example.focusandstudy.model.database.DBHandler;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.jetbrains.annotations.TestOnly;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarMonthlyFragment extends Fragment{

    private FragmentCalendarMonthlyBinding binding;
    LinearLayout tasks;
    CalendarView calendarView;
    CompactCalendarView compactCalendarView;
    TextView monthTitle;
    SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private DBHandler mDBHandler;
    ImageView create_task_button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCalendarMonthlyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mDBHandler = new DBHandler(this.getActivity());

        compactCalendarView = binding.compactCalendarView;
        monthTitle = binding.monthTitle;
        tasks = binding.tasks;
        create_task_button = binding.createTask;

        String date = dateFormatMonth.format(Calendar.getInstance().getTime());
        monthTitle.setText(date);
        compactCalendarView.setUseThreeLetterAbbreviation(false);

        List<Task> tasks =  mDBHandler.getTasksFromUser(mDBHandler.getSharedPrefUserId(this.getActivity()));

        Calendar calendar = Calendar.getInstance();
        //Returns current time in millis
        long timeMilli2 = calendar.getTimeInMillis();
        Event ev1 = new Event(getResources().getColor(R.color.green1), timeMilli2);
        compactCalendarView.addEvent(ev1);

        for (int i=0; i<tasks.size(); i++){
            String myDate = tasks.get(i).getDate();
            String type = tasks.get(i).getType();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date temp_date = null;
            try {
                temp_date = sdf.parse(myDate);
                long dateInMillis = temp_date.getTime();
                Event ev;
                if (type.equals("Examen")){
                    ev = new Event(getResources().getColor(R.color.purple_500), dateInMillis);
                }
                else {
                    ev = new Event(getResources().getColor(R.color.green1), dateInMillis);
                }
                compactCalendarView.addEvent(ev);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        java.util.Date utilDate = Calendar.getInstance().getTime();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        updateTasks(sqlDate);

        compactCalendarView.setUseThreeLetterAbbreviation(false);

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(java.util.Date dateClicked) {
                java.sql.Date sqlDate = new java.sql.Date(dateClicked.getTime());
                updateTasks(sqlDate);
            }

            @Override
            public void onMonthScroll(java.util.Date firstDayOfNewMonth) {
                monthTitle.setText(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });

        create_task_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newTaskActivity = new Intent(getActivity(), NewTaskActivity.class);
                startActivity(newTaskActivity);
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        java.util.Date utilDate = Calendar.getInstance().getTime();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        updateTasks(sqlDate);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateTasks(Date date){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        if (tasks.getChildCount() > 0)
            tasks.removeAllViews();

        List<Task> tasks =  mDBHandler.getTaskFromDateFromUser(date, mDBHandler.getSharedPrefUserId(this.getActivity()));

        for(int i = 0; i < tasks.size(); i++){
            Fragment newFragment = new CalendarMonthlyItemFragment(tasks.get(i));
            ft.add(R.id.tasks, newFragment);
        }
        ft.commit();

    }

}