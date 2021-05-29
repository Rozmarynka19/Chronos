package com.example.chronosapp.ui.itemList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chronosapp.R;

import java.util.Hashtable;

public class EditTaskActivity extends AppCompatActivity implements EditTaskBackgroundTaskListener,
                                                                    GetTaskBackgroundTaskListener{

    //TODO: dates with time - deadline, notificationDate
    //TODO: recurring - list of days in which deadline is set anew

    private String itemID, taskName, taskDescription, priority ="";
    private EditText taskNameEditText, taskDescriptionEditText;
    private RadioButton radioHighPriority, radioNormalPriority, radioLowPriority;
    private Button editTaskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Intent details = getIntent();
        itemID = details.getStringExtra("itemid");
        taskName = details.getStringExtra("itemName");
        Toast.makeText(this, itemID, Toast.LENGTH_SHORT).show();

        taskNameEditText = findViewById(R.id.nameOfTaskEditText);
        taskDescriptionEditText = findViewById(R.id.taskDescriptionEditText);
        editTaskButton = findViewById(R.id.sendNewTaskToDbButton);
        editTaskButton.setText("Edit Task");

        radioHighPriority = findViewById(R.id.radioHighPriority);
        radioNormalPriority = findViewById(R.id.radioNormalPriority);
        radioLowPriority = findViewById(R.id.radioLowPriority);

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
                    Toast.makeText(this, "high piority", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.radioNormalPriority:
                if (checked)
                {
                    priority = "2";
                    Toast.makeText(this, "normal piority", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.radioLowPriority:
                if (checked)
                {
                    priority = "1";
                    Toast.makeText(this, "low piority", Toast.LENGTH_SHORT).show();
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

    public void sendNewTaskToDb(View view) {
        taskName = taskNameEditText.getText().toString();
        taskDescription = taskDescriptionEditText.getText().toString();

        EditTaskBackgroundTask editTaskBackgroundTask = new EditTaskBackgroundTask(this);
//        []= {itemid, itemname, itemtype, deadline, desc, recurring, notificationDate, piority}
        editTaskBackgroundTask.execute(itemID, taskName, ItemTypes.Task.toString(), "", taskDescription, "", "", priority);
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
        priority = tableOfTaskDetails.get("Task_Priority");
        setRadioButton();
    }
}