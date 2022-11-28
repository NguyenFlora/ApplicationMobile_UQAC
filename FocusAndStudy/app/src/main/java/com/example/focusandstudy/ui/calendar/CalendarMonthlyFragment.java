package com.example.focusandstudy.ui.calendar;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.focusandstudy.R;
import com.example.focusandstudy.databinding.FragmentCalendarMonthlyBinding;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.DateFormat;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCalendarMonthlyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        compactCalendarView = binding.compactCalendarView;
        monthTitle = binding.monthTitle;
        tasks = binding.tasks;

        String date = dateFormatMonth.format(Calendar.getInstance().getTime());
        monthTitle.setText(date);
        compactCalendarView.setUseThreeLetterAbbreviation(false);

        String dateToString = df.format(Calendar.getInstance().getTime());
        updateTasks(dateToString);

        compactCalendarView.setUseThreeLetterAbbreviation(false);

        //Set an event for Teachers' Professional Day 2016 which is 21st of October

        Event ev1 = new Event(getResources().getColor(R.color.green1), 1669838086000L);
        compactCalendarView.addEvent(ev1);

        Event ev2 = new Event(getResources().getColor(R.color.purple_500), 1669838086000L);
        compactCalendarView.addEvent(ev2);

        //List<Event> events = compactCalendarView.getEvents(1669445948); // can also take a Date object

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                String dateToString = df.format(dateClicked);
                updateTasks(dateToString);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                monthTitle.setText(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateTasks(String date){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        if(((LinearLayout)tasks).getChildCount() > 0)
            ((LinearLayout)tasks).removeAllViews();

        for(int i = 0; i < 10; i++){
            Fragment newFragment = new CalendarMonthlyItemFragment(date);
            ft.add(R.id.tasks, newFragment);

        }
        ft.commit();
    }
}