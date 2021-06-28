package com.example.chronosapp.ui.itemList;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
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
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chronosapp.NotificationBuilder;
import com.example.chronosapp.NotificationPublisher;
import com.example.chronosapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.chronosapp.NotificationBuilder.CHANNEL_2_ID;
import java.util.Collections;

public class AddTaskActivity extends AppCompatActivity implements AddTaskBackgroundTaskListener {

    //TODO: dates with time - deadline, notificationDate
    //TODO: recurring - list of days in which deadline is set anew


    private String listID, taskName, taskDescription, taskDate, taskTime, piority="";
    private EditText taskNameEditText, taskDescriptionEditText, taskDateEditField, taskTimeEditField;
    private NotificationManagerCompat notificationManager;

    private RadioButton radioButtonLowPriority;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_layout);

        notificationManager = NotificationManagerCompat.from(this);
        Intent details = getIntent();
        listID = details.getStringExtra("listid");
        //Toast.makeText(this, listID, Toast.LENGTH_SHORT).show();
        initDatePicker();
        initTimePicker();
        taskNameEditText = findViewById(R.id.nameOfTaskEditText);
        taskDescriptionEditText = findViewById(R.id.taskDescriptionEditText);
        radioButtonLowPriority = findViewById(R.id.radioLowPriority);
        onRadioButtonClicked(radioButtonLowPriority);

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

        mSubtasksRecyclerView = findViewById(R.id.subtasksRecyclerView);
        mSubtasksRecyclerView.setLayoutManager(new LinearLayoutManager(mSubtasksRecyclerView.getContext()));
//        mSubtasksRecyclerView.setHasFixedSize(true);
        mSubtasks = new ArrayList<>();
        mSubtasksAdapter = new SubtasksAdapter(this,mSubtasks);
        mSubtasksRecyclerView.setAdapter(mSubtasksAdapter);

        newSubtaskEditText = findViewById(R.id.addTaskLayoutAddSubtaskEditText);
        addSubtask = findViewById(R.id.addTaskLayoutAddSubtaskButton);
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
        applySubtasks();

        mAttachmentsRecyclerView = findViewById(R.id.attachmentsRecyclerView);
        mAttachmentsRecyclerView.setLayoutManager(new LinearLayoutManager(mAttachmentsRecyclerView.getContext()));
//        mAttachmentsRecyclerView.setHasFixedSize(true);
        mAttachments = new ArrayList<>();
        mAttachmentsAdapter = new AttachmentsAdapter(this,mAttachments);
        mAttachmentsRecyclerView.setAdapter(mAttachmentsAdapter);

        addAttachment = findViewById(R.id.addTaskLayoutAddAttachmentButton);
        addAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: browse for device files

                mAttachments.add("Test");
                applyAttachments();
            }
        });
        applyAttachments();


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

    private void initDatePicker() {
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

    private String createDate(int day, int month, int year) {
        String picked_day = String.valueOf(day);
        if (picked_day.length() == 1)
            picked_day = "0" + picked_day;

        String picked_month = String.valueOf(month);
        if (picked_month.length() == 1)
            picked_month = "0" + picked_month;

        return picked_day + "-" + picked_month + "-" + year;
    }

    private String createTime(int hour, int minute) {

        String picked_hour = String.valueOf(hour);
        if (picked_hour.length() == 1)
            picked_hour = "0" + picked_hour;

        String picked_minute = String.valueOf(minute);
        if (picked_minute.length() == 1)
            picked_minute = "0" + picked_minute;

        return picked_hour + ":" + picked_minute;
    }

    /**
     * Enum piority:
     * * 1 - lowPiority
     * * 2 - normalPiority
     * * 3 - highPiority
     */

    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked.
        switch (view.getId()) {
            case R.id.radioHighPriority:
                if (checked) {
                    piority = "3";
                    //Toast.makeText(this, "high piority", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.radioNormalPriority:
                if (checked) {
                    piority = "2";
                    //Toast.makeText(this, "normal piority", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.radioLowPriority:
                if (checked) {
                    piority = "1";
                    //Toast.makeText(this, "low piority", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                // Do nothing.
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

        if (taskName.isEmpty()) {
            taskNameEditText.setError("Task name is required");
            taskNameEditText.requestFocus();
            return;
        }

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

//        Log.d("sendNewTaskToDb - date",date);
//        Log.d("sendNewTaskToDb - time",time);

//        String deadline = date + " " + time;


        Notification not = createNotification(taskName, taskDescription);
        Calendar myCalendar = Calendar.getInstance();
        Date date = myCalendar.getTime() ;
        scheduleNotification(not, 60 * 1000 );

        AddTaskBackgroundTask addTaskBackgroundTask = new AddTaskBackgroundTask(this);
        addTaskBackgroundTask.execute(listID, taskName, ItemTypes.Task.toString(), fullDeadlineDate, taskDescription, "", "", piority);
    }

    private Notification createNotification(String taskName, String taskDescription) {
        String title = taskName;
        String message = taskDescription;

        return new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        //notificationManager.notify(1, notification);
    }

    private void scheduleNotification(Notification notification, long delay) {
        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.CHANNEL_1_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, delay, pendingIntent);
    }


    @Override
    public void refreshListOfItems() {
        setResult(RESULT_OK, new Intent());
        this.finish();
    }


    public void onCheckboxClicked(View view) {
    }
}