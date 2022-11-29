package com.example.focusandstudy.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputFilter;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.focusandstudy.LocaleHelper;
import com.example.focusandstudy.R;

import com.example.focusandstudy.controller.MainActivity;
import com.example.focusandstudy.databinding.FragmentCalendarMonthlyBinding;
import com.example.focusandstudy.databinding.FragmentSettingsBinding;

import java.util.Locale;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    LinearLayout language;
    TextView language_text;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        language = binding.language;
        language_text = binding.languageText;


        // handle changing the "from" type
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(), language_text);
                popupMenu.getMenuInflater().inflate(R.menu.language_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Context context;
                        Resources resources;

                        int i = menuItem.getOrder();
                        if (i == 0) { // hexadecimal
                            context = LocaleHelper.setLocale(getActivity(), "fr");
                            resources = context.getResources();
                            language_text.setText(resources.getString(R.string.language));
                        } else {
                            language_text.setText("EN");

                        }
                        return true;
                    }
                });

                popupMenu.show();
            }
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
/*
    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        finish();
        startActivity(refresh);
    }
 */
}