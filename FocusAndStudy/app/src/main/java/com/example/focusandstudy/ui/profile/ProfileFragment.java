package com.example.focusandstudy.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.focusandstudy.R;
import com.example.focusandstudy.databinding.FragmentProfileBinding;
import com.example.focusandstudy.model.User;
import com.example.focusandstudy.model.database.DBHandler;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    private TextView m_name;
    private TextView m_level;
    private TextView m_XP;
    private TextView m_number_of_hours_today;
    private TextView m_day_streak;
    private TextView m_number_of_badges;
    private TextView m_number_of_hours_per_week;
    private ProgressBar m_progress_bar;
    private int user_id;
    private User mUser;
    private int progressBarLevel;

    private DBHandler mDBHandler;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel notificationsViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mDBHandler = new DBHandler(this.getActivity());

        m_name = (TextView) view.findViewById(R.id.name);
        m_level = (TextView) view.findViewById(R.id.level);
        m_XP = (TextView) view.findViewById(R.id.XP);
        m_number_of_hours_today = (TextView) view.findViewById(R.id.number_of_hours_today);
        m_day_streak = (TextView) view.findViewById(R.id.day_streak);
        m_number_of_badges = (TextView) view.findViewById(R.id.number_of_badges);
        m_number_of_hours_per_week = (TextView) view.findViewById(R.id.number_of_hours_per_week);
        m_progress_bar = (ProgressBar) view.findViewById(R.id.progress_bar);
        setStatisticsOnView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void setStatisticsOnView(){
        user_id =  mDBHandler.getSharedPrefUserId(this.getActivity());
        mUser = mDBHandler.getUserFromId(user_id);
        int XP = mUser.getXP();
        int level = 0;
        if(XP>0) {
            level = (int) Math.floor(XP / 1000);
        }
        m_progress_bar.setMax(1000);
        m_progress_bar.setProgress(XP);
        m_name.setText(mDBHandler.getSharedPrefUsername(this.getActivity()));
        m_level.setText(String.valueOf(level));
        m_XP.setText(String.valueOf(mUser.getXP()));
        m_number_of_hours_today.setText(getTimeFormatWithMin(mUser.getDailyTime()));
        m_day_streak.setText(String.valueOf(mUser.getDayStreak()));
        m_number_of_badges.setText(String.valueOf(mUser.getNbBadges()));
        m_number_of_hours_per_week.setText(getTimeFormatWithMin(mUser.getWeeklyTime()));
    }

    public String getTimeFormatWithMin(int min){
        int hours = min / 60;
        int minutes = min % 60;
        return (hours +" h " +minutes);
    }
}