package com.example.chronosapp.ui.itemList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chronosapp.R;

import java.sql.Time;
import java.time.Clock;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity implements AddTaskBackgroundTaskListener {

    //TODO: dates with time - deadline, notificationDate
    //TODO: recurring - list of days in which deadline is set anew

    private String listID, taskName, taskDescription, piority="";
    private EditText taskNameEditText, taskDescriptionEditText;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private EditText timeField;
    private EditText dateField;

    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_layout);

        Intent details = getIntent();
        listID =  details.getStringExtra("listid");
        Toast.makeText(this, listID, Toast.LENGTH_SHORT).show();
        initDatePicker();
        initTimePicker();
        taskNameEditText = findViewById(R.id.nameOfTaskEditText);
        taskDescriptionEditText = findViewById(R.id.taskDescriptionEditText);

        linearLayout = findViewById(R.id.go_back);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        timeField = findViewById(R.id.taskTimeEditText);
        timeField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    timePickerDialog.show();
                else
                    timePickerDialog.hide();
            }
        });

        dateField = findViewById(R.id.taskDateEditText);
        dateField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    datePickerDialog.show();
                else
                    datePickerDialog.hide();
            }
        });
    }

    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = createTime(hourOfDay, minute);
                timeField.setText(time);
            }
        };
        Calendar cal = Calendar.getInstance();
        int hour = cal.HOUR_OF_DAY;
        int minute = cal.MINUTE;

        int style = AlertDialog.THEME_HOLO_DARK;
        timePickerDialog = new TimePickerDialog(this, style, timeSetListener, hour, minute, false);

    }

    /**
     * Enum piority:
     *      * 1 - lowPiority
     *      * 2 - normalPiority
     *      * 3 - highPiority
     * */

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = createDate(day, month, year);
                dateField.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String createDate(int day, int month, int year)
    {
        String picked_day = String.valueOf(day);
        if(picked_day.length() == 1)
            picked_day = "0" + picked_day;

        String picked_month = String.valueOf(month);
        if(picked_month.length() == 1)
            picked_month = "0" + picked_month;

        return picked_day + ":" + picked_month + ":" + year;
    }

    private String createTime(int hour, int minute){

        String picked_hour = String.valueOf(hour);
        if(picked_hour.length() == 1)
            picked_hour = "0" + picked_hour;

        String picked_minute = String.valueOf(minute);
        if(picked_minute.length() == 1)
            picked_minute = "0" + picked_minute;

        return picked_hour + ":" + picked_minute;
    }


    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked.
        switch (view.getId()) {
            case R.id.radioHighPriority:
                if (checked)
                {
                    piority = "3";
                    Toast.makeText(this, "high piority", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.radioNormalPriority:
                if (checked)
                {
                    piority = "2";
                    Toast.makeText(this, "normal piority", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.radioLowPriority:
                if (checked)
                {
                    piority = "1";
                    Toast.makeText(this, "low piority", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                // Do nothing.
                break;
        }
    }

    public void sendNewTaskToDb(View view) {
        taskName = taskNameEditText.getText().toString();
        taskDescription = taskDescriptionEditText.getText().toString();

        if(taskName.isEmpty()){
            taskNameEditText.setError("Task name is required");
            taskNameEditText.requestFocus();
            return;
        }

        String date = dateField.getText().toString().trim();
        if(date.isEmpty()){
            dateField.setError("Task date is required");
            dateField.requestFocus();
            return;
        }

        String time = timeField.getText().toString().trim();
        if(time.isEmpty()){
            timeField.setError("Task time is required");
            timeField.requestFocus();
            return;
        }


        AddTaskBackgroundTask addTaskBackgroundTask = new AddTaskBackgroundTask(this);
//        []= {listid, itemname, itemtype, deadline, desc, recurring, notificationDate, piority}
        addTaskBackgroundTask.execute(listID, taskName, ItemTypes.Task.toString(), "", taskDescription, "", "", piority);
    }

    @Override
    public void refreshListOfItems() {
        setResult(RESULT_OK,new Intent());
        this.finish();
    }
}