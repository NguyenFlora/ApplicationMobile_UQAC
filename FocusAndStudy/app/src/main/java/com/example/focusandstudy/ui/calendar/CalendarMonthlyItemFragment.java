package com.example.focusandstudy.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.focusandstudy.R;
import com.example.focusandstudy.databinding.FragmentCalendarMonthlyItemBinding;

public class CalendarMonthlyItemFragment extends Fragment {

    private FragmentCalendarMonthlyItemBinding binding;
    private String date;
    RelativeLayout item;
    TextView task_text, to_do_text;
    ImageView task_image, round_validated_image;

    public CalendarMonthlyItemFragment(String date) {
        this.date = date;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentCalendarMonthlyItemBinding.inflate(inflater, container, false);
        task_text = binding.taskText;
        to_do_text = binding.toDo;
        task_image = binding.taskImage;
        round_validated_image = binding.roundValidated;
        item = binding.item;

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        item.setBackgroundResource(R.drawable.box8);
        task_text.setText(date);
        task_image.setImageResource(R.drawable.purple_round);
        to_do_text.setVisibility(View.INVISIBLE);
        round_validated_image.setVisibility(View.VISIBLE);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}