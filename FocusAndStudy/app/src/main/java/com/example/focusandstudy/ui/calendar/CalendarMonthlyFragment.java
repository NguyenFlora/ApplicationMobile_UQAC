package com.example.focusandstudy.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.focusandstudy.R;
import com.example.focusandstudy.databinding.FragmentCalendarMonthlyBinding;

import java.util.Calendar;
import java.util.Date;

public class CalendarMonthlyFragment extends Fragment{

    private FragmentCalendarMonthlyBinding binding;
    LinearLayout tasks;
    CalendarView calendarView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCalendarMonthlyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        calendarView = binding.simpleCalendarView;
        tasks = binding.tasks;
        calendarView.setDate(Calendar.getInstance().getTimeInMillis(),false,true);
        String date = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "-" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "-" + Calendar.getInstance().get(Calendar.YEAR);
        updateTasks(date);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {

                String date = dayOfMonth + "-" + (month + 1) + "-" + year;
                updateTasks(date);
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
        if(((LinearLayout) tasks).getChildCount() > 0)
            ((LinearLayout) tasks).removeAllViews();

        for(int i = 0; i < 10; i++){
            Fragment newFragment = new CalendarMonthlyItemFragment(date);
            ft.add(R.id.tasks, newFragment);

        }
        ft.commit();
    }
}