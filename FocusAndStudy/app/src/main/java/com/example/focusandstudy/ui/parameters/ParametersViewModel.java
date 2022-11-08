package com.example.focusandstudy.ui.parameters;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ParametersViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ParametersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is parameters fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}