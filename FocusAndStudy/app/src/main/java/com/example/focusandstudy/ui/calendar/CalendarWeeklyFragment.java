package com.example.focusandstudy.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.focusandstudy.databinding.FragmentCalendarWeeklyBinding;

public class CalendarWeeklyFragment extends Fragment{

    private FragmentCalendarWeeklyBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        CalendarWeeklyViewModel calendarWeeklyViewModel =
                new ViewModelProvider(this).get(CalendarWeeklyViewModel.class);

        binding = FragmentCalendarWeeklyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textCalendarWeekly;
        calendarWeeklyViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}