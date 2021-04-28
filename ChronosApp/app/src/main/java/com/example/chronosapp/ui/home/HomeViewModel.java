package com.example.chronosapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private String user_name = "User Name";

    protected String welcome_text = "" +
            "Welcome " + user_name + "\n"+
            "Swipe to see what you have to do!";

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(welcome_text);
    }

    public LiveData<String> getText() {
        return mText;
    }
}