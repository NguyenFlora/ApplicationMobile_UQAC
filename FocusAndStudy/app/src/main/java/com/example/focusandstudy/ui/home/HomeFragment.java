package com.example.focusandstudy.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.focusandstudy.R;
import com.example.focusandstudy.controller.PomodoroActivity;
import com.example.focusandstudy.controller.PresentationActivity;
import com.example.focusandstudy.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment  implements View.OnClickListener{

    private FragmentHomeBinding binding;
    private Button m_start_a_new_session;
    private ImageView m_discover_the_pomodoro_cycle;
    public HomeFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        m_start_a_new_session = (Button) view.findViewById(R.id.start_a_new_session);
        m_discover_the_pomodoro_cycle = (ImageView) view.findViewById(R.id.discover_the_pomodoro_cycle);

        m_start_a_new_session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newSessionActivity = new Intent(getActivity(), PomodoroActivity.class);
                startActivity(newSessionActivity);
                System.out.println("new session");
            }
        });

        m_discover_the_pomodoro_cycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent presentationActivity = new Intent(getActivity(), PresentationActivity.class);
                startActivity(presentationActivity);
                System.out.println("presentation pomodoro");
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onClick(View view) {

    }
}