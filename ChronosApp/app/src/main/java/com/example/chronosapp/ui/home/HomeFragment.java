package com.example.chronosapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.chronosapp.R;
import com.example.chronosapp.ui.itemList.Item;
import com.example.chronosapp.ui.list.ListFragment;
import com.example.chronosapp.ui.list.ListViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements ItemsDetailsForHomeFragmentSetListener{

    private HomeViewModel homeViewModel;
    private ArrayList<Item> mItemArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void getItemsForHomeFragment(ArrayList<Item> mItemArrayList) {
        this.mItemArrayList = mItemArrayList;

        Log.d("getItemsForHomeFragment","----------");
        for (Item item: mItemArrayList) {
            Log.d("getItemsForHomeFragment - id",item.getItemID());
            Log.d("getItemsForHomeFragment - title",item.getTitle());
            Log.d("getItemsForHomeFragment - type",item.getType());
            Log.d("getItemsForHomeFragment - deadline",item.getDeadline());
            Log.d("getItemsForHomeFragment - priority",item.getPriority());
        }
    }
}