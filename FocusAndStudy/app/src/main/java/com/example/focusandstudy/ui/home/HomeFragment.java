package com.example.focusandstudy.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.focusandstudy.R;
import com.example.focusandstudy.controller.PomodoroActivity;
import com.example.focusandstudy.databinding.FragmentHomeBinding;
import com.example.focusandstudy.ui.PresentationFragment;

public class HomeFragment extends Fragment  implements View.OnClickListener{

    private FragmentHomeBinding binding;

    public HomeFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button newSession = (Button) view.findViewById(R.id.start_a_new_session);
        newSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newSessionActivity = new Intent(getActivity(), PomodoroActivity.class);
                startActivity(newSessionActivity);
                System.out.println("new session");
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static PresentationFragment newInstance() {
        PresentationFragment fragment = new PresentationFragment();
        return fragment;
    }

    @Override
    public void onClick(View view) {

    }
}