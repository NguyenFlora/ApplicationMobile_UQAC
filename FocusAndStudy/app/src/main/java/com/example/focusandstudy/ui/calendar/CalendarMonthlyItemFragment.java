package com.example.focusandstudy.ui.calendar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.focusandstudy.controller.NewTaskActivity;
import com.example.focusandstudy.controller.TaskDetailsActivity;
import com.example.focusandstudy.databinding.FragmentCalendarMonthlyItemBinding;
import com.example.focusandstudy.model.Task;

public class CalendarMonthlyItemFragment extends Fragment {

    private FragmentCalendarMonthlyItemBinding binding;
    private Task task;
    RelativeLayout item;
    TextView task_text, to_do_text;
    ImageView task_image, round_validated_image;
    public CalendarMonthlyItemFragment(){

    }

    public CalendarMonthlyItemFragment(Task task) {
        this.task = task;
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

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = getActivity().getSharedPreferences("CurrentTask", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("name", task.getName());
                editor.putInt("id", task.getId());
                editor.apply();

                Intent taskDetailsActivity = new Intent(getActivity(), TaskDetailsActivity.class);
                startActivity(taskDetailsActivity);
            }
        });

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        task_text.setText(task.getName());

        if (task.getStatus().equals("1")){
            item.setBackgroundResource(R.drawable.box8);
            to_do_text.setVisibility(View.INVISIBLE);
            round_validated_image.setVisibility(View.VISIBLE);
        }
        else {
            item.setBackgroundResource(R.drawable.box7);
            to_do_text.setVisibility(View.VISIBLE);
            round_validated_image.setVisibility(View.INVISIBLE);
        }

        if (task.getType().equals("examen")){
            task_image.setImageResource(R.drawable.purple_round);
        }
        else{
            task_image.setImageResource(R.drawable.green_round);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}