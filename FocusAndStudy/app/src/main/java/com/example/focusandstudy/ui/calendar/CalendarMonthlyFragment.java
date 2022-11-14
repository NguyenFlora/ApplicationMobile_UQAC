package com.example.focusandstudy.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.focusandstudy.databinding.FragmentCalendarMonthlyBinding;

<<<<<<< HEAD:FocusAndStudy/app/src/main/java/com/example/focusandstudy/ui/calendar/CalendarFragment.java
public class CalendarFragment extends Fragment {
=======
public class CalendarMonthlyFragment extends Fragment{
>>>>>>> 4573de3 (Merge pull request #9 from NguyenFlora/feat/set_up_next_activities):FocusAndStudy/app/src/main/java/com/example/focusandstudy/ui/calendar/CalendarMonthlyFragment.java

    private FragmentCalendarMonthlyBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        CalendarWeeklyViewModel calendarWeeklyViewModel =
                new ViewModelProvider(this).get(CalendarWeeklyViewModel.class);

        binding = FragmentCalendarMonthlyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textCalendarMonthly;
        calendarWeeklyViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}