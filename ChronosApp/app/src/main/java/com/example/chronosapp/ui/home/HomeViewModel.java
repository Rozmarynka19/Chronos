package com.example.chronosapp.ui.home;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class HomeViewModel extends ViewModel  {

    private MutableLiveData<String> mText;
    private String user_name = "User";
    protected String welcome_text = "" +
            "Welcome" + user_name + "\n"+
            "Swipe to see what you have to do!"+ "\n" +
            "Tasks below are finished soon";

    public HomeViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is home fragment - our\n \"Hi Mark," +
//                " today you've got to do xyz part");
        mText.setValue(welcome_text);
    }
    public String setText(){
        return this.welcome_text;
    }

    public LiveData<String> getText() {
        return mText;
    }
}