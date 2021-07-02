package com.example.chronosapp.ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chronosapp.MainMainActivity;
import com.example.chronosapp.R;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class CalendarView extends AppCompatActivity implements View.OnClickListener, Serializable {

    private Button addFilteredTasks;
    private TextView calendarDate, textViewTasks;
    private DatePickerDialog datePickerDialog;
    private String[] resultItemsSplittedRows;
    private boolean isCalendarShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);


        addFilteredTasks = (Button)findViewById(R.id.show_tasks);
        calendarDate = (TextView)findViewById(R.id.calendarTasks);
        textViewTasks = (TextView)findViewById(R.id.textViewTask);
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
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = dateFormat.format(calendar.getTime());
        calendarDate.setText(date);

        @SuppressLint("WrongConstant")
        SharedPreferences sharedPreferences = getSharedPreferences("userDataSharedPref", Context.MODE_APPEND);
        String sharedUserId = sharedPreferences.getString("userid","");

        String resultLists = "";
        com.example.chronosapp.ui.home.CalendarBackgroundTask calendarBackgroundTaskLists = new com.example.chronosapp.ui.home.CalendarBackgroundTask();
        try {
            resultLists = calendarBackgroundTaskLists.execute("lists",sharedUserId).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] resultListsSplitted = resultLists.split("\n");
        int i = 2;
        ArrayList<String> arrayList = new ArrayList<>();
        while(i < resultListsSplitted.length - 1) {
            arrayList.add(resultListsSplitted[i]);
            i += 2;
        }

        String resultItems = "";
        String args = "";
        com.example.chronosapp.ui.home.CalendarBackgroundTask calendarBackgroundTaskItems = new com.example.chronosapp.ui.home.CalendarBackgroundTask();
        for (String s : arrayList) {
            args = args.concat(s).concat(" ");
        }
        try {
            resultItems = calendarBackgroundTaskItems.execute("items",args).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        resultItemsSplittedRows = resultItems.split("\n");
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
                textViewTasks.setText("");
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

    @SuppressLint("SetTextI18n")
    private void addTasks(View v){
        if(!calendarDate.getText().toString().trim().isEmpty()) {
            String multiLineText = "";
            ArrayList<String[]> tasksToShow = new ArrayList<>();
            for (String s : resultItemsSplittedRows) {
                String prv_shit = "<!-- End";
                if (!s.substring(0, 8).equals(prv_shit)) {
                    String[] resultItemsSplittedRowToCols = s.split(",");
                    String[] extractDate = resultItemsSplittedRowToCols[1].split(" ");
                    if(extractDate[0].equals(calendarDate.getEditableText().toString())) {
                        //multiLineText = multiLineText.concat(resultItemsSplittedRowToCols[0].concat("\n"));
                        String[] dateToAdd = {resultItemsSplittedRowToCols[0],extractDate[1]};
                        tasksToShow.add(dateToAdd);
                    }
                }
            }

            Collections.sort(tasksToShow, new Comparator<String[]>() {
                @Override
                public int compare(String[] o1, String[] o2) {
                    try {
                        return new SimpleDateFormat("hh:mm").parse(o1[1]).compareTo(new SimpleDateFormat("hh:mm").parse(o2[1]));
                    } catch (ParseException e) {
                        return 0;
                    }
                }
            });
            for (String[] toAdd : tasksToShow) {
                multiLineText = multiLineText.concat(toAdd[0].concat(" ").concat(toAdd[1]).concat("\n"));
            }


            if(multiLineText.length() == 0) {
                textViewTasks.setTextColor(Color.RED);
                textViewTasks.setText("No tasks on this day");

            }
            else {
                textViewTasks.setTextColor(Color.WHITE);
                textViewTasks.setText(multiLineText);
            }

        }
    }
}
