package com.example.chronosapp.ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.chronosapp.MainMainActivity;
import com.example.chronosapp.R;
import com.example.chronosapp.ui.list.ListFragment;

import java.util.ArrayList;
import java.util.List;

public class Calendar  extends AppCompatActivity implements View.OnClickListener{

    private Button addFilteredTasks;
    private TextView calendarDate;
    private DatePickerDialog datePickerDialog;

    private boolean isCalendarShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        addFilteredTasks = (Button)findViewById(R.id.show_tasks);
        calendarDate = (TextView)findViewById(R.id.calendarTasks);
        initDatePicker();

        calendarDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate(v);
            }
        });

        addFilteredTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTasks(v);
            }
        });

        LinearLayout back_arrow = findViewById(R.id.calendar_back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed(){
        startActivity( new Intent(this, MainMainActivity.class) );
        finish();
    }

    @Override
    public void onClick(View v) { }

    private void pickDate(View v){
        if(isCalendarShown)
            return;

        datePickerDialog.show();
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = createDate(day, month, year);
                calendarDate.setText(date);
                isCalendarShown = false;
            }
        };
        java.util.Calendar cal = java.util.Calendar.getInstance();
        int year = cal.get(java.util.Calendar.YEAR);
        int month = cal.get(java.util.Calendar.MONTH);
        int day = cal.get(java.util.Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String createDate(int day, int month, int year) {
        String picked_day = String.valueOf(day);
        if (picked_day.length() == 1)
            picked_day = "0" + picked_day;

        String picked_month = String.valueOf(month);
        if (picked_month.length() == 1)
            picked_month = "0" + picked_month;

        return picked_day + "-" + picked_month + "-" + year;
    }

    private void addTasks(View v){
        if(calendarDate.getText().toString().trim().isEmpty())
            return;
        
    }

}
