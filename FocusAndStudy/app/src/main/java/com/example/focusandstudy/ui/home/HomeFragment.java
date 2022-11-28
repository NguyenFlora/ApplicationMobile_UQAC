package com.example.focusandstudy.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.focusandstudy.R;
import com.example.focusandstudy.controller.PomodoroActivity;
import com.example.focusandstudy.controller.PresentationActivity;
import com.example.focusandstudy.databinding.FragmentHomeBinding;
import com.example.focusandstudy.model.User;
import com.example.focusandstudy.model.database.DBHandler;
import com.example.focusandstudy.ui.profile.ProfileFragment;

public class HomeFragment extends Fragment  implements View.OnClickListener{

    private FragmentHomeBinding binding;
    private Button m_start_a_new_session;
    private ImageView m_discover_the_pomodoro_cycle;
    private TextView m_level_text;
    private TextView m_XP_text;
    private TextView m_number_of_hours_today_text;
    private TextView m_number_of_hours_per_week_text;
    private DBHandler mDBHandler;
    private int user_id;
    private User mUser;
    public HomeFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        m_start_a_new_session = (Button) view.findViewById(R.id.start_a_new_session);
        m_discover_the_pomodoro_cycle = (ImageView) view.findViewById(R.id.discover_the_pomodoro_cycle);
        m_level_text = (TextView) view.findViewById(R.id.level_text);
        m_XP_text = (TextView) view.findViewById(R.id.score_text);
        m_number_of_hours_today_text = (TextView) view.findViewById(R.id.number_of_hours_today_text);
        m_number_of_hours_per_week_text = (TextView) view.findViewById(R.id.number_of_hours_per_week_text);

        mDBHandler = new DBHandler(this.getActivity());

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
    public void onStart() {
        super.onStart();
        setStatisticsOnView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onClick(View view) {

    }
    private void setStatisticsOnView(){
        user_id =  mDBHandler.getSharedPrefUserId(this.getActivity());
        mUser = mDBHandler.getUserFromId(user_id);
        int XP = mUser.getXP();
        int level = 0;
        if(XP>0) {
            level = (int) Math.floor(XP / 1000);
        }
        m_level_text.setText(String.valueOf(level));
        m_XP_text.setText(String.valueOf(mUser.getXP()));
        m_number_of_hours_today_text.setText(getTimeFormatWithMin(mUser.getDailyTime()));
        m_number_of_hours_per_week_text.setText(getTimeFormatWithMin(mUser.getWeeklyTime()));
    }

    public String getTimeFormatWithMin(int min){
        int hours = min / 60;
        int minutes = min % 60;
        return (hours +" h " +minutes);
    }
}