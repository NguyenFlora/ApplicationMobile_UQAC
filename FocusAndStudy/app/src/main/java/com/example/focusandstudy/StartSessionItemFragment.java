package com.example.focusandstudy;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.focusandstudy.controller.StartSessionActivity;
import com.example.focusandstudy.databinding.FragmentCalendarMonthlyItemBinding;
import com.example.focusandstudy.databinding.FragmentStartSessionItemBinding;

public class StartSessionItemFragment extends Fragment {

    FragmentStartSessionItemBinding binding;
    LinearLayout task;
    TextView task_text, task_date;
    int id;
    boolean clicked = false;

    public StartSessionItemFragment(int i) {
        this.id = i;
    }

    public void setClicked(boolean clicked){
        this.clicked = clicked;
    }

    public boolean getClicked(){
        return clicked;
    }

    public LinearLayout getTask(){
        return task;
    }

    public TextView getTask_text(){
        return task_text;
    }

    public TextView getTask_date() {
        return task_date;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentStartSessionItemBinding.inflate(inflater, container, false);
        task = binding.task;
        task_text = binding.taskText;
        task_date = binding.taskDate;

        task.setId(R.id.task + id);
        task_text.setText("Devoir d'anglais");
        task_date.setText("7 décembre 2022");

        if (id==0){
            setClicked(true);
            task.setBackgroundResource(R.drawable.box);
            task_text.setTextColor(getResources().getColor(R.color.green2));
            Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.montserrat_medium);
            task_text.setTypeface(typeface);
            task_date.setTextColor(getResources().getColor(R.color.gray));
        }

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
