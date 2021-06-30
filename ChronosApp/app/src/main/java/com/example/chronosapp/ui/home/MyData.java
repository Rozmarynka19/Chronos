package com.example.chronosapp.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

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

        LinearLayout back_arrow = (LinearLayout)findViewById(R.id.mydata_back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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
        if(nameEdit.getText().toString().trim().isEmpty()){
            nameEdit.setError("Bill name can not be empty!");
            nameEdit.requestFocus();
            return;
        }

        if(!isValidAccountNumber(accountEdit.getText().toString().trim())){
            accountEdit.setError("An invalid account number was entered");
            accountEdit.requestFocus();
            return;
        }

        Intent intent = new Intent(this, generatedQRActivity.class);
        intent.putExtra("Account_Name", nameEdit.getText().toString());
        intent.putExtra("Account_Number", accountEdit.getText().toString());
        startActivity(intent);
    }

    public void saveData(View view) {
        bill_Account_Name = nameEdit.getText().toString();
        bill_Account_Number = accountEdit.getText().toString();

        if(bill_Account_Name.isEmpty()){
            nameEdit.setError("Bill name can not be empty!");
            nameEdit.requestFocus();
            return;
        }

        if(!isValidAccountNumber(bill_Account_Number)) {
            accountEdit.setError("An invalid account number was entered");
            accountEdit.requestFocus();
            return;
        }

        MyDataBackgroundTask addDataBackgroundTask = new MyDataBackgroundTask(this);
        addDataBackgroundTask.execute("SAVE",user_ID, bill_Account_Name, bill_Account_Number);
    }

    public boolean isValidAccountNumber(String accountNumber){
        if(accountNumber.matches("\\d{26}"))
            return true;

        return false;
    }

}
