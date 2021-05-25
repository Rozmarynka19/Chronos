package com.example.chronosapp.ui.itemList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chronosapp.R;

public class AddTaskActivity extends AppCompatActivity implements AddTaskBackgroundTaskListener {

    //TODO: dates with time - deadline, notificationDate
    //TODO: recurring - list of days in which deadline is set anew

    private String listID, taskName, taskDescription, piority="";
    private EditText taskNameEditText, taskDescriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_layout);

        Intent details = getIntent();
        listID =  details.getStringExtra("listid");
        Toast.makeText(this, listID, Toast.LENGTH_SHORT).show();

        taskNameEditText = findViewById(R.id.nameOfTaskEditText);
        taskDescriptionEditText = findViewById(R.id.taskDescriptionEditText);
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