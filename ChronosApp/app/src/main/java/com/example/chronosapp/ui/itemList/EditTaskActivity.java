package com.example.chronosapp.ui.itemList;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chronosapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

public class EditTaskActivity extends AppCompatActivity implements EditTaskBackgroundTaskListener,
                                                                    GetTaskBackgroundTaskListener,
                                                                 GetSubtasksBackgroundTaskListener,
                                                                GetAttachmentsBackgroundTaskListener{

    //TODO: dates with time - deadline, notificationDate

    private String itemID, taskName, taskDescription, taskDate, taskTime, priority="";
    private EditText taskNameEditText, taskDescriptionEditText, taskDateEditField, taskTimeEditField;
    private RadioButton radioHighPriority, radioNormalPriority, radioLowPriority;
    private Button editTaskButton;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private LinearLayout linearLayout;

    private RecyclerView mSubtasksRecyclerView;
    private ArrayList<String> mSubtasks;
    private SubtasksAdapter mSubtasksAdapter;
    private Button addSubtask;
    private EditText newSubtaskEditText;

    private RecyclerView mAttachmentsRecyclerView;
    private ArrayList<String> mAttachments;
    private AttachmentsAdapter mAttachmentsAdapter;
    private Button addAttachment;

    private ArrayList<Recurrence> mRecurrence = new ArrayList<>();

    private ArrayList<CheckBox> mRecurrenceCheckBoxes = new ArrayList<>();

    private static final int LOAD_FILE_RESULTS = 100,
            PERMISSION_REQUEST_CODE = 33;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task_layout);

        List<String> mDaysOfTheWeek = List.of("Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday");

        for (String str: mDaysOfTheWeek) {
            mRecurrence.add(new Recurrence(str, false));
        }

        List<Integer> checkBoxesIDs = List.of(R.id.editTaskLayoutMondayCheckbox,
                                            R.id.editTaskLayoutTuesdayCheckbox,
                                            R.id.editTaskLayoutWednesdayCheckbox,
                                            R.id.editTaskLayoutThursdayCheckbox,
                                            R.id.editTaskLayoutFridayCheckbox,
                                            R.id.editTaskLayoutSaturdayCheckbox,
                                            R.id.editTaskLayoutSundayCheckbox);

        for (Integer id:checkBoxesIDs) {
            mRecurrenceCheckBoxes.add(findViewById(id));
        }

        Intent details = getIntent();
        itemID = details.getStringExtra("itemid");
        taskName = details.getStringExtra("itemName");
        //Toast.makeText(this, itemID, Toast.LENGTH_SHORT).show();

        initDatePicker();
        initTimePicker();

        taskNameEditText = findViewById(R.id.editTaskLayoutNameOfTaskEditText);
        taskDescriptionEditText = findViewById(R.id.editTaskLayoutTaskDescriptionEditText);
        editTaskButton = findViewById(R.id.sendEditedTaskToDbButton);
        editTaskButton.setText("Edit Task");

        radioHighPriority = findViewById(R.id.editTaskLayoutRadioHighPriority);
        radioNormalPriority = findViewById(R.id.editTaskLayoutRadioNormalPriority);
        radioLowPriority = findViewById(R.id.editTaskLayoutRadioLowPriority);

        linearLayout = findViewById(R.id.go_backEditTaskLayout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        taskTimeEditField = findViewById(R.id.editTaskLayoutTaskTimeEditText);
        taskTimeEditField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    timePickerDialog.show();
                else
                    timePickerDialog.hide();
            }
        });

        taskDateEditField = findViewById(R.id.editTaskLayoutTaskDateEditText);
        taskDateEditField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    datePickerDialog.show();
                else
                    datePickerDialog.hide();
            }
        });

        mSubtasksRecyclerView = findViewById(R.id.editTaskLayoutSubtasksRecyclerView);
        mSubtasksRecyclerView.setLayoutManager(new LinearLayoutManager(mSubtasksRecyclerView.getContext()));
//        mSubtasksRecyclerView.setHasFixedSize(true);
        mSubtasks = new ArrayList<>();
        mSubtasksAdapter = new SubtasksAdapter(this,mSubtasks);
        mSubtasksRecyclerView.setAdapter(mSubtasksAdapter);

        newSubtaskEditText = findViewById(R.id.editTaskLayoutAddSubtaskEditText);
        addSubtask = findViewById(R.id.editTaskLayoutAddSubtaskButton);
        addSubtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newSubtaskEditText == null)
                {
                    Log.d("newSubtaskEditText","null");
                    return;
                }
                else
                    Log.d("newSubtaskEditText","not null");

                String newSubtask = newSubtaskEditText.getText().toString();
                Log.d("subtask",newSubtask);
                mSubtasks.add(newSubtask);
                applySubtasks();
            }
        });
//        applySubtasks();

        mAttachmentsRecyclerView = findViewById(R.id.editTaskLayoutAttachmentsRecyclerView);
        mAttachmentsRecyclerView.setLayoutManager(new LinearLayoutManager(mAttachmentsRecyclerView.getContext()));
//        mAttachmentsRecyclerView.setHasFixedSize(true);
        mAttachments = new ArrayList<>();
        mAttachmentsAdapter = new AttachmentsAdapter(this,mAttachments);
        mAttachmentsRecyclerView.setAdapter(mAttachmentsAdapter);

        addAttachment = findViewById(R.id.editTaskLayoutAddAttachmentButton);
        addAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkPermissions())
                    requestPermissions();
                filePicker();

//                mAttachments.add("Test");
//                applyAttachments();
            }
        });
//        applyAttachments();

        getTask(itemID);
    }

    private void filePicker()
    {
        Toast.makeText(EditTaskActivity.this,"File Picker Call", Toast.LENGTH_SHORT).show();
        Intent picker = new Intent(Intent.ACTION_GET_CONTENT);
        picker.setType("*/*");
        startActivityForResult(Intent.createChooser(picker,"Choose a file"),LOAD_FILE_RESULTS);

    }

    private void requestPermissions()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(EditTaskActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE))
            Toast.makeText(EditTaskActivity.this, "Please give permissions to browse files",Toast.LENGTH_SHORT).show();
        else
            ActivityCompat.requestPermissions(EditTaskActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    private boolean checkPermissions()
    {
        int result = ContextCompat.checkSelfPermission(EditTaskActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode)
        {
            case PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(EditTaskActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(EditTaskActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("AddTaskActivity - check", String.valueOf( resultCode == RESULT_OK &&  requestCode == LOAD_FILE_RESULTS ));
        if( resultCode == RESULT_OK &&  requestCode == LOAD_FILE_RESULTS ) {
            String filename = getRealNameFromUri(data.getData(), EditTaskActivity.this);
            String filepath = getRealPathFromURI(data.getData());//getRealPathFromURI_API19(filename, this, data.getData());//getRealPathFromURI(data.getData());
            Log.d("File Name: ", filename);
            Log.d("FilePath: ", filepath);

            mAttachments.add(filename);
            applyAttachments();
        }
    }


    public String getRealNameFromUri(Uri uri, Activity activity)
    {
//        String[] projection = {MediaStore.Files.FileColumns.DATA};
        Cursor cursor = activity.getContentResolver().query(uri,null,null,null,null);
        if(cursor == null)
            return  uri.getPath();
        else
        {
            cursor.moveToFirst();
            int id = cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME); //DISPLAY_NAME
//            int id = cursor.getColumnIndex( projection[0] ); //DISPLAY_NAME
            return cursor.getString(id);
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = 0;
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
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
            case R.id.editTaskLayoutRadioHighPriority:
                if (checked)
                {
                    priority = "3";
                    //Toast.makeText(this, "high piority", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.editTaskLayoutRadioNormalPriority:
                if (checked)
                {
                    priority = "2";
                    //Toast.makeText(this, "normal piority", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.editTaskLayoutRadioLowPriority:
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

    public void sendEditedTaskToDb(View view) {
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

        StringBuilder recurrence = new StringBuilder();
        for (int i=0;i<mRecurrence.size();i++) {
            if(mRecurrence.get(i).isSet())
                recurrence.append(mRecurrence.get(i).getDayOfTheWeek()).append(",");
        }
        Log.d("recurrence length",String.valueOf(recurrence.length()));
        if(recurrence.length() > 0)
            recurrence.deleteCharAt(recurrence.length()-1);

        Log.d("recurrence",recurrence.toString());

        StringBuilder subtasks = new StringBuilder();
        for (int i=0;i<mSubtasks.size();i++) {
            subtasks.append(mSubtasks.get(i)).append(",");
        }

        if(subtasks.length() > 0)
            subtasks.deleteCharAt(subtasks.length()-1);

        Log.d("subtasks",subtasks.toString());

        StringBuilder attachments = new StringBuilder();
        for (int i=0;i<mAttachments.size();i++) {
            attachments.append(mAttachments.get(i)).append(",");
        }

        if(attachments.length() > 0)
            attachments.deleteCharAt(attachments.length()-1);

        Log.d("attachments",attachments.toString());

        EditTaskBackgroundTask editTaskBackgroundTask = new EditTaskBackgroundTask(this);
//        []= {itemid, itemname, itemtype, deadline, desc, recurring, notificationDate, piority, subtasks, attachments}
        editTaskBackgroundTask.execute(itemID, taskName, ItemTypes.Task.toString(), fullDeadlineDate, taskDescription, recurrence.toString(), "", priority, subtasks.toString(), attachments.toString());
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

        String[] separatedRecurrency = tableOfTaskDetails.get("Task_Recurring").split(",");
        for(int i=0;i<separatedRecurrency.length; i++)
        {
            for (int j=0; j<mRecurrence.size(); j++) {
                if(separatedRecurrency[i].equals(mRecurrence.get(j).getDayOfTheWeek()))
                    mRecurrence.get(j).set(true);
            }
        }
        setRecurrenceCheckBoxes();

        getSubtasksFromDb(itemID);
    }

    void getSubtasksFromDb(String itemID)
    {
        GetSubtasksBackgroundTask getSubtasksBackgroundTask = new GetSubtasksBackgroundTask(this);
        getSubtasksBackgroundTask.execute(itemID);
    }

    @Override
    public void getSubtasks(ArrayList<String> subtasks) {
        if(subtasks != null)
        {
            mSubtasks = subtasks;
            applySubtasks();
        }

        getAttachmentsFromDb(itemID);
    }

    private void getAttachmentsFromDb(String itemID) {
        GetAttachmentsBackgroundTask getAttachmentsBackgroundTask = new GetAttachmentsBackgroundTask(this);
        getAttachmentsBackgroundTask.execute(itemID);
    }

    @Override
    public void getAttachments(ArrayList<String> attachments) {
        if(attachments != null)
        {
            mAttachments = attachments;
            applyAttachments();
        }
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

    /**
     * Apply fetched subtasks from database to the view
     */
    private void applySubtasks() {
//        // Get the resources from the XML file.
//        String[] listItemTitles = getResources()
//                .getStringArray(R.array.listItemTitles);
//        String[] listItemDescriptions = getResources()
//                .getStringArray(R.array.listItemDescription);
//        TypedArray listItemsBackgrounds = getResources()
//                .obtainTypedArray(R.array.listItemBackgrounds);
//
//        // Clear the existing data (to avoid duplication).
//        mListItems.clear();

//        // Create the ArrayList of List Item objects with the titles and
//        // information about each list
//        for (int i = 0; i < listItemTitles.length; i++) {
//            mListItems.add(new ListItem(listItemTitles[i], listItemDescriptions[i],
//                    listItemsBackgrounds.getResourceId(i, 0)));
//        }
//
//        // Recycle the typed array.
//        listItemsBackgrounds.recycle();
        mSubtasksAdapter.setSubtasks(mSubtasks);
        Log.d("size of array of list item: ",String.valueOf(mSubtasks.size()));
        Log.d("size of list item adapter: ",String.valueOf(mSubtasksAdapter.getItemCount()));


        // Notify the adapter of the change.
//        mlistItemAdapter.notifyDataSetChanged();

        // Helper class for creating swipe to dismiss and drag and drop
        // functionality.
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            /**
             * Defines the drag and drop functionality.
             *
             * @param recyclerView The RecyclerView that contains the list items
             * @param viewHolder The ListViewViewHolder that is being moved
             * @param target The ListViewViewHolder that you are switching the
             *               original one with.
             * @return true if the item was moved, false otherwise
             */
            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                // Get the from and to positions.
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();

                // Swap the items and notify the adapter.
                Collections.swap(mSubtasks, from, to);
                mSubtasksAdapter.notifyItemMoved(from, to);
                return true;
            }

            /**
             * Defines the swipe to dismiss functionality.
             *
             * @param viewHolder The viewholder being swiped.
             * @param direction The direction it is swiped in.
             */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                 int direction) {
                // Remove from database.
//                removeListFromDatabase(viewHolder, mListItems.get(viewHolder.getAdapterPosition()));

                // Remove the item from the dataset.
                mSubtasks.remove(viewHolder.getAdapterPosition());

                // Notify the adapter.
                mSubtasksAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });

        // Attach the helper to the RecyclerView.
        helper.attachToRecyclerView(mSubtasksRecyclerView);
    }

    /**
     * Apply fetched attachments from database to the view
     */
    private void applyAttachments() {
//        // Get the resources from the XML file.
//        String[] listItemTitles = getResources()
//                .getStringArray(R.array.listItemTitles);
//        String[] listItemDescriptions = getResources()
//                .getStringArray(R.array.listItemDescription);
//        TypedArray listItemsBackgrounds = getResources()
//                .obtainTypedArray(R.array.listItemBackgrounds);
//
//        // Clear the existing data (to avoid duplication).
//        mListItems.clear();

//        // Create the ArrayList of List Item objects with the titles and
//        // information about each list
//        for (int i = 0; i < listItemTitles.length; i++) {
//            mListItems.add(new ListItem(listItemTitles[i], listItemDescriptions[i],
//                    listItemsBackgrounds.getResourceId(i, 0)));
//        }
//
//        // Recycle the typed array.
//        listItemsBackgrounds.recycle();


        for (String str:mAttachments) {
            Log.d("mAttachments",str);
        }

        mAttachmentsAdapter.setAttachments(mAttachments);
        Log.d("size of array of list item: ",String.valueOf(mAttachments.size()));
        Log.d("size of list item adapter: ",String.valueOf(mAttachmentsAdapter.getItemCount()));


        // Notify the adapter of the change.
//        mlistItemAdapter.notifyDataSetChanged();

        // Helper class for creating swipe to dismiss and drag and drop
        // functionality.
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            /**
             * Defines the drag and drop functionality.
             *
             * @param recyclerView The RecyclerView that contains the list items
             * @param viewHolder The ListViewViewHolder that is being moved
             * @param target The ListViewViewHolder that you are switching the
             *               original one with.
             * @return true if the item was moved, false otherwise
             */
            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                // Get the from and to positions.
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();

                // Swap the items and notify the adapter.
                Collections.swap(mAttachments, from, to);
                mAttachmentsAdapter.notifyItemMoved(from, to);
                return true;
            }

            /**
             * Defines the swipe to dismiss functionality.
             *
             * @param viewHolder The viewholder being swiped.
             * @param direction The direction it is swiped in.
             */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                 int direction) {
                // Remove from database.
//                removeListFromDatabase(viewHolder, mListItems.get(viewHolder.getAdapterPosition()));

                // Remove the item from the dataset.
                mAttachments.remove(viewHolder.getAdapterPosition());

                // Notify the adapter.
                mAttachmentsAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });

        // Attach the helper to the RecyclerView.
        helper.attachToRecyclerView(mAttachmentsRecyclerView);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        int index = -1;

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.editTaskLayoutMondayCheckbox:
                index = 0;
                break;
            case R.id.editTaskLayoutTuesdayCheckbox:
                index = 1;
                break;
            case R.id.editTaskLayoutWednesdayCheckbox:
                index = 2;
                break;
            case R.id.editTaskLayoutThursdayCheckbox:
                index = 3;
                break;
            case R.id.editTaskLayoutFridayCheckbox:
                index = 4;
                break;
            case R.id.editTaskLayoutSaturdayCheckbox:
                index = 5;
                break;
            case R.id.editTaskLayoutSundayCheckbox:
                index = 6;
                break;
        }

        if(index != -1)
                mRecurrence.get(index).set(checked);


//        for (Recurrence re:mRecurrence) {
//            Log.d("onCheckboxClicked - dayOfTheWeek",re.getDayOfTheWeek());
//            Log.d("onCheckboxClicked - isSet",String.valueOf(re.isSet()));
//        }
    }

    private void setRecurrenceCheckBoxes()
    {
        for(int i=0; i<mRecurrence.size(); i++)
           if(mRecurrence.get(i).isSet())
               mRecurrenceCheckBoxes.get(i).setChecked(true);
    }
}