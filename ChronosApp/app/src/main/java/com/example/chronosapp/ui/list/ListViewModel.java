package com.example.chronosapp.ui.list;

import android.content.res.TypedArray;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chronosapp.R;

import java.util.ArrayList;

public class ListViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is list fragment - lists all user todolists");

    }

    public LiveData<String> getText() {
        return mText;
    }
}