package com.example.focusandstudy.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.focusandstudy.R;
import com.example.focusandstudy.databinding.FragmentCalendarMonthlyBinding;

public class CalendarMonthlyFragment extends Fragment{

    private FragmentCalendarMonthlyBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCalendarMonthlyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

        for(int i = 0; i < 10; i++){
            Fragment newFragment = new CalendarMonthlyItemFragment(i);
            ft.add(R.id.tasks, newFragment);

        }
        ft.commit();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}