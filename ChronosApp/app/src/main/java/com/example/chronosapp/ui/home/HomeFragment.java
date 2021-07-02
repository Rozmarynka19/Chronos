package com.example.chronosapp.ui.home;

import android.app.Activity;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chronosapp.R;
import com.example.chronosapp.ui.itemList.Item;
import com.example.chronosapp.ui.itemList.ItemAdapter;
import com.example.chronosapp.ui.list.ListItem;
import com.example.chronosapp.ui.list.ListItemAdapter;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class HomeFragment extends Fragment implements ItemsDetailsForHomeFragmentSetListener{
    private MutableLiveData<String> mText;
    private ViewGroup root;
    private HomeViewModel homeViewModel;
    private ArrayList<Item> mItemArrayList;
    private ArrayList<Item> mListItems;
    private RecyclerView mRecyclerView;
    private ItemAdapter mItemAdapter;
    private String sharedLogin;

    private Activity mainMainActivity;

    public HomeFragment(Activity activity)
    {
        this.mainMainActivity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        root = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        @SuppressLint("WrongConstant")
        SharedPreferences sharedPreferences = root.getContext().getSharedPreferences("userDataSharedPref", root.getContext().MODE_APPEND);
        if(sharedPreferences!=null && !(sharedPreferences.getString("login","").equals("")))
        {
            sharedLogin = sharedPreferences.getString("login","");
        }
        textView.setText("Welcome " + sharedLogin + "!" + "\n" +
                         "Swipe to see what you have to do!"+ "\n" +
                         "Tasks below are finished soon");
        return root;
    }
    @Override
    public void getItemsForHomeFragment(ArrayList<Item> mItemArrayList) {
        this.mItemArrayList = mItemArrayList;
        //root = (ViewGroup) inflater.inflate(R.layout.fragment_homeList, container, false);

        ItemsDetailsForHomeFragmentSetListener listener = (ItemsDetailsForHomeFragmentSetListener) mainMainActivity;
        listener.getItemsForHomeFragment(mItemArrayList);

        Log.d("getItemsForHomeFragment","----------");
        for (Item item: mItemArrayList) {
            Log.d("getItemsForHomeFragment - id",item.getItemID());
            Log.d("getItemsForHomeFragment - title",item.getTitle());
            Log.d("getItemsForHomeFragment - type",item.getType());
            Log.d("getItemsForHomeFragment - deadline",item.getDeadline());
            Log.d("getItemsForHomeFragment - priority",item.getPriority());
        }
        Date todayDate = java.util.Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(todayDate);

        String todayDateStr = "";
        todayDateStr += strDate.substring(8, 10);//day
        todayDateStr += "-";
        todayDateStr += strDate.substring(5, 7);//mth
        todayDateStr += "-";
        todayDateStr += strDate.substring(0, 4);//yr

        Date tomorrowDate = new Date();
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(tomorrowDate);
        c.add(java.util.Calendar.DATE, 1);
        tomorrowDate = c.getTime();

        strDate = dateFormat.format(tomorrowDate);
        String tomorrowDateStr="";
        tomorrowDateStr += strDate.substring(8, 10);
        tomorrowDateStr += "-";
        tomorrowDateStr += strDate.substring(5, 7);
        tomorrowDateStr += "-";
        tomorrowDateStr += strDate.substring(0, 4);

        mRecyclerView = root.findViewById(R.id.ToDoListsRecyclerViewFragmentHome);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        mListItems = new ArrayList<>();
        for(Item item : mItemArrayList){
            String dataOfItem = item.getDeadline().substring(0, 10);
            if(dataOfItem.equals(todayDateStr) || dataOfItem.equals(tomorrowDateStr) ){
                mListItems.add(item);
            }
        }
        mItemAdapter = new ItemAdapter(root.getContext(), mListItems, 0);
        mRecyclerView.setAdapter(mItemAdapter);


        sortingByDeadline();
    }
    public void sortingByDeadline() {
        System.out.println("HELLO");
        Collections.sort(mItemArrayList, new Comparator<Item>(){
            public int compare(Item o1, Item o2)
            {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                LocalDate o1Date = LocalDate.parse(o1.getDeadline(), formatter);
                LocalDate o2Date = LocalDate.parse(o2.getDeadline(), formatter);
                if (o1Date.isBefore(o2Date))
                    return -1;
                if (o1Date.isAfter(o2Date))
                    return 1;
                else
                    return 0;
            }
        });
        //System.out.println("       " + mItemArrayList.get(0).getDeadline());
        //mItemAdapter.notifyDataSetChanged();
        mListItems.clear();
        Date todayDate = java.util.Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(todayDate);

        String todayDateStr = "";
        todayDateStr += strDate.substring(8, 10);//day
        todayDateStr += "-";
        todayDateStr += strDate.substring(5, 7);//mth
        todayDateStr += "-";
        todayDateStr += strDate.substring(0, 4);//yr

        Date tomorrowDate = new Date();
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(tomorrowDate);
        c.add(java.util.Calendar.DATE, 1);
        tomorrowDate = c.getTime();

        strDate = dateFormat.format(tomorrowDate);
        String tomorrowDateStr="";
        tomorrowDateStr += strDate.substring(8, 10);
        tomorrowDateStr += "-";
        tomorrowDateStr += strDate.substring(5, 7);
        tomorrowDateStr += "-";
        tomorrowDateStr += strDate.substring(0, 4);

        for(Item item : mItemArrayList){
            String dataOfItem = item.getDeadline().substring(0, 10);
            if(dataOfItem.equals(todayDateStr) || dataOfItem.equals(tomorrowDateStr) ){
                mListItems.add(item);
            }
        }

    }
}