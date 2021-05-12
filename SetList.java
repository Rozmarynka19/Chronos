package com.example.tasks;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SetList extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_list);
    }
    EditText nameOfTaskInput;
    String nameOfTaskStr= "NULL";
    RadioGroup radioGroup;
    RadioButton radioButton;
    public void createNewButton(View view) {
        //pobranie danych od użytkownika
        nameOfTaskInput = (EditText) findViewById(R.id.nameOfTask);
        nameOfTaskStr = nameOfTaskInput.getText().toString();

        radioGroup = findViewById(R.id. radioGroupPriority);
        int radioID = radioGroup.getCheckedRadioButtonId();
        radioButton=findViewById(radioID);
        int color = radioButton.getCurrentTextColor();

        Button myButton = new Button(this);
        myButton.setText(nameOfTaskStr);
        myButton.setBackgroundColor(color);
        setContentView(R.layout.activity_main);
        LinearLayout layout = findViewById(R.id.lista);
        layout.addView(myButton);
    }
    public void showToast(String text){
        Toast.makeText(SetList.this, text, Toast.LENGTH_SHORT).show();
    }
}
