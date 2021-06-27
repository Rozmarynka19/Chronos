package com.example.chronosapp.ui.itemList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chronosapp.R;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.Objects;

public class EditTaskActivity extends AppCompatActivity implements EditTaskBackgroundTaskListener,
                                                                    GetTaskBackgroundTaskListener{

    //TODO: dates with time - deadline, notificationDate
    //TODO: recurring - list of days in which deadline is set anew

    private String itemID, taskName, taskDescription, taskDate, taskTime, priority="";
    private EditText taskNameEditText, taskDescriptionEditText, taskDateEditField, taskTimeEditField;
    private RadioButton radioHighPriority, radioNormalPriority, radioLowPriority;
    private Button editTaskButton;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_layout);

        Intent details = getIntent();
        itemID = details.getStringExtra("itemid");
        taskName = details.getStringExtra("itemName");
        //Toast.makeText(this, itemID, Toast.LENGTH_SHORT).show();

        initDatePicker();
        initTimePicker();

        taskNameEditText = findViewById(R.id.nameOfTaskEditText);
        taskDescriptionEditText = findViewById(R.id.taskDescriptionEditText);
        editTaskButton = findViewById(R.id.sendNewTaskToDbButton);
        editTaskButton.setText("Edit Task");

        radioHighPriority = findViewById(R.id.radioHighPriority);
        radioNormalPriority = findViewById(R.id.radioNormalPriority);
        radioLowPriority = findViewById(R.id.radioLowPriority);

        linearLayout = findViewById(R.id.go_back);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        taskTimeEditField = findViewById(R.id.taskTimeEditText);
        taskTimeEditField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    timePickerDialog.show();
                else
                    timePickerDialog.hide();
            }
        });

        taskDateEditField = findViewById(R.id.taskDateEditText);
        taskDateEditField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    datePickerDialog.show();
                else
                    datePickerDialog.hide();
            }
        });

        getTask(itemID);
    }

    /**
     * Enum piority:
     *      * 1 - lowPiority
     *      * 2 - normalPiority
     *      * 3 - highPiority
     * */

    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked.
        switch (view.getId()) {
            case R.id.radioHighPriority:
                if (checked)
                {
                    priority = "3";
                    //Toast.makeText(this, "high piority", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.radioNormalPriority:
                if (checked)
                {
                    priority = "2";
                    //Toast.makeText(this, "normal piority", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.radioLowPriority:
                if (checked)
                {
                    priority = "1";
                    //Toast.makeText(this, "low piority", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                // Do nothing.
                break;
        }
    }

    private void setRadioButton()
    {
        switch(priority){
            case "3":
                radioHighPriority.setChecked(true);
                Log.d("setRadioButton - priority", "high piority");
                break;
            case "2":
                radioNormalPriority.setChecked(true);
                Log.d("setRadioButton - priority", "normal piority");
                break;
            case "1":
                radioLowPriority.setChecked(true);
                Log.d("setRadioButton - priority", "low piority");
                break;

        }
    }

    private int check_errors() {
        int errors = 0;
        if(taskName.isEmpty() || taskDate.isEmpty() || taskTime.isEmpty()) {
            if(taskName.isEmpty()) {
                taskNameEditText.setError("Task name is required");
                errors += 1;
            }
            if(taskDate.isEmpty()) {
                taskDateEditField.setError("Task date is required");
                errors += 1;
            }
            if(taskTime.isEmpty()) {
                taskTimeEditField.setError("Task time is required");
                errors += 1;
            }
        }
        return errors;
    }

    public void sendNewTaskToDb(View view) {
        taskName = taskNameEditText.getText().toString();
        taskDescription = taskDescriptionEditText.getText().toString();
        taskDate = taskDateEditField.getText().toString();
        taskTime = taskTimeEditField.getText().toString();

        if(check_errors() > 0) {
            return;
        }

        String fullDeadlineDate = "";
        fullDeadlineDate = fullDeadlineDate.concat(taskDate).concat(" ").concat(taskTime);
        System.out.println("DATE: " + fullDeadlineDate);
//        String date = dateField.getText().toString().trim();
//        if(date.isEmpty()){
//            dateField.setError("Task date is required");
//            dateField.requestFocus();
//            return;
//        }
//
//        String time = timeField.getText().toString().trim();
//        if(time.isEmpty()){
//            timeField.setError("Task time is required");
//            timeField.requestFocus();
//            return;
//        }
//
//        Log.d("sendNewTaskToDb - date",date);
//        Log.d("sendNewTaskToDb - time",time);

//        String deadline = date + " " + time;+

        EditTaskBackgroundTask editTaskBackgroundTask = new EditTaskBackgroundTask(this);
//        []= {itemid, itemname, itemtype, deadline, desc, recurring, notificationDate, piority}
        editTaskBackgroundTask.execute(itemID, taskName, ItemTypes.Task.toString(), fullDeadlineDate, taskDescription, "", "", priority);
    }

    @Override
    public void refreshListOfItems() {
        setResult(RESULT_OK,new Intent());
        this.finish();
    }

    private void getTask(String itemID)
    {
        GetTaskBackgroundTask getTaskBackgroundTask = new GetTaskBackgroundTask(this);
        getTaskBackgroundTask.execute(itemID);
    }

    @Override
    public void getTaskDetails(Hashtable<String, String> tableOfTaskDetails) {
        Log.d("getTaskDetails - desc", tableOfTaskDetails.get("Task_Desc"));
        Log.d("getTaskDetails - priority", tableOfTaskDetails.get("Task_Priority"));
        taskNameEditText.setText(taskName);
        taskDescriptionEditText.setText(tableOfTaskDetails.get("Task_Desc"));

        String[] separateDateTime = Objects.requireNonNull(tableOfTaskDetails.get("Task_Deadline")).split(" ");
        if(separateDateTime.length != 0) {
            taskDateEditField.setText(separateDateTime[0]);
            taskTimeEditField.setText(separateDateTime[1]);
        }
        priority = tableOfTaskDetails.get("Task_Priority");
        setRadioButton();
    }

    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = createTime(hourOfDay, minute);
                taskTimeEditField.setText(time);
            }
        };
        Calendar cal = Calendar.getInstance();
        int hour = cal.HOUR_OF_DAY;
        int minute = cal.MINUTE;

        int style = AlertDialog.THEME_HOLO_DARK;
        timePickerDialog = new TimePickerDialog(this, style, timeSetListener, hour, minute, false);

    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = createDate(day, month, year);
                taskDateEditField.setText(date);
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

        return picked_day + "-" + picked_month + "-" + year;
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
}