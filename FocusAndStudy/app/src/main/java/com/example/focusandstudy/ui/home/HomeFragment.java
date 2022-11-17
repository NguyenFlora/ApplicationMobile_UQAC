package com.example.focusandstudy.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.focusandstudy.R;
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