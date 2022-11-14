package com.example.focusandstudy.ui.calendar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CalendarMonthlyViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CalendarMonthlyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is calendar monthly fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}