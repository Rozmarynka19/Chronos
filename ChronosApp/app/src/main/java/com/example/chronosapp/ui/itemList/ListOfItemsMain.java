package com.example.chronosapp.ui.itemList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chronosapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListOfItemsMain extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<TaskItem> mtaskItemArrayList;
    private TaskItemAdapter taskItemAdapter;
    private String sharedUserId;

    private Animation rotateOpen, rotateClose, fromBottom, toBottom;

    private FloatingActionButton addNewItemFab;
    private Button addNewTaskButton, addNewBillButton;

    private boolean isAddNewItemButtonClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_tasks);

        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);

        addNewTaskButton = findViewById(R.id.addNewTaskButton);
        addNewBillButton = findViewById(R.id.addNewBillButton);
        addNewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "addNewTaskButton",Toast.LENGTH_SHORT).show();
            }
        });
        addNewBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "addNewBillButton",Toast.LENGTH_SHORT).show();
            }
        });

        addNewItemFab = findViewById(R.id.itemListFab);
        addNewItemFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddNewItemFabClicked();
            }
        });

        mRecyclerView = findViewById(R.id.itemListRecycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mtaskItemArrayList = new ArrayList<>();
        taskItemAdapter = new TaskItemAdapter(this,mtaskItemArrayList);
        mRecyclerView.setAdapter(taskItemAdapter);

        @SuppressLint("WrongConstant")
        SharedPreferences sharedPreferences = this.getSharedPreferences("userDataSharedPref", Context.MODE_APPEND);
        sharedUserId = sharedPreferences.getString("userid","");
    }

    private void onAddNewItemFabClicked() {
        setVisibility();
        setAnimation();
        setClickable();
        isAddNewItemButtonClicked = !isAddNewItemButtonClicked;
    }

    private void setAnimation() {
        if(!isAddNewItemButtonClicked)
        {
            addNewTaskButton.startAnimation(fromBottom);
            addNewBillButton.startAnimation(fromBottom);
            addNewItemFab.startAnimation(rotateOpen);
        }
        else
        {
            addNewTaskButton.startAnimation(toBottom);
            addNewBillButton.startAnimation(toBottom);
            addNewItemFab.startAnimation(rotateClose);
        }
    }

    private void setVisibility() {
        if(!isAddNewItemButtonClicked)
        {
            addNewTaskButton.setVisibility(View.VISIBLE);
            addNewBillButton.setVisibility(View.VISIBLE);
        }
        else
        {
            addNewTaskButton.setVisibility(View.INVISIBLE);
            addNewBillButton.setVisibility(View.INVISIBLE);
        }
    }

    private void setClickable()
    {
        if(!isAddNewItemButtonClicked)
        {
            addNewTaskButton.setClickable(true);
            addNewBillButton.setClickable(true);
        }
        else
        {
            addNewTaskButton.setClickable(false);
            addNewBillButton.setClickable(false);
        }
    }
}
