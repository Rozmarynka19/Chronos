package com.example.chronosapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment - our\n \"Hi Mark," +
                " today you've got to do xyz part");
    }

    public LiveData<String> getText() {
        return mText;
    }
}