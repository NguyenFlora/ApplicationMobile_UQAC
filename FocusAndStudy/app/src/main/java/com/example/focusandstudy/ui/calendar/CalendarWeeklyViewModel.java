package com.example.focusandstudy.ui.calendar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CalendarWeeklyViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CalendarWeeklyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is calendar weekly fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}