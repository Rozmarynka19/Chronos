package com.example.chronosapp.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chronosapp.MainMainActivity;
import com.example.chronosapp.R;

public class MyData extends AppCompatActivity {
    private String user_ID, bill_Account_Name, bill_Account_Number;
    private EditText nameEdit, accountEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_data);

        nameEdit = findViewById(R.id.NameEdit);
        accountEdit = findViewById(R.id.accountEdit);

        @SuppressLint("WrongConstant")
        SharedPreferences sharedPreferences = getSharedPreferences("userDataSharedPref", MODE_APPEND);
        user_ID = sharedPreferences.getString("userid","");
        com.example.chronosapp.ui.home.MyDataBackgroundTask myDataBackgroundTask = new com.example.chronosapp.ui.home.MyDataBackgroundTask(this);
        String result = "";
        try {
            result = myDataBackgroundTask.execute("GET", sharedPreferences.getString("userid","")).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] resultSplitted;
        resultSplitted = result.split("\n");
        if(resultSplitted.length > 1) {
            nameEdit.setText(resultSplitted[1]);
            accountEdit.setText(resultSplitted[2]);
        }
    }

    @Override
    public void onBackPressed(){
        startActivity( new Intent(this, MainMainActivity.class) );
        finish();
    }

    public void generateQR(View view) {
        Intent intent = new Intent(this, generatedQRActivity.class);
        intent.putExtra("Account_Name", nameEdit.getText().toString());
        intent.putExtra("Account_Number", accountEdit.getText().toString());
        startActivity(intent);
    }

    public void saveData(View view) {

        bill_Account_Name = nameEdit.getText().toString();
        bill_Account_Number = accountEdit.getText().toString();

        if(bill_Account_Number.length() < 26) {
            accountEdit.setError("MUST BE 26 NUMBERS");
            accountEdit.requestFocus();
        }

        MyDataBackgroundTask addDataBackgroundTask = new MyDataBackgroundTask(this);
        addDataBackgroundTask.execute("SAVE",user_ID, bill_Account_Name, bill_Account_Number);
    }
}
