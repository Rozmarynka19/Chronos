package com.example.chronosapp.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.chronosapp.R;

public class VerifyActivity extends AppCompatActivity implements View.OnClickListener {

    private Button verifyButton, reSendEmail;
    private EditText editTextVerify;
    private String sharedLogin, sharedEmail, sharedPhone, sharedUserId, sharedKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        verifyButton = (Button) findViewById(R.id.VerifyProceed);
        verifyButton.setOnClickListener(this);
        editTextVerify = (EditText) findViewById(R.id.verify);
        reSendEmail = (Button) findViewById(R.id.repeatSendEmail);
        reSendEmail.setOnClickListener(this);
        sendEmail();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.VerifyProceed:
                verify();
                System.out.println(sharedKey);
                break;
            case R.id.repeatSendEmail:
                sendEmail();
                break;
        }
    }

    private void verify() {
        String verifyText = (editTextVerify.getText().toString().trim());

        if (verifyText.isEmpty()) {
            editTextVerify.setError("code requried");
            editTextVerify.requestFocus();
            return;
        }

        if(verifyText.compareTo(sharedKey)!=0){
            editTextVerify.setError("Wrong code");
            editTextVerify.requestFocus();
            return;
        }

        String type = "setVerified";
        VerifyBackgroundTask backgroundTask = new VerifyBackgroundTask(this);
        backgroundTask.execute(type, "1", "");
        this.startActivity(new Intent(this, com.example.chronosapp.MainMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    private void sendEmail() {
        String type = "email";
//        com.example.chronosapp.login.BackgroundTask backgroundTask = new com.example.chronosapp.login.BackgroundTask(getApplicationContext());
        @SuppressLint("WrongConstant") SharedPreferences sharedPreferences = getSharedPreferences("userDataSharedPref", MODE_APPEND);
        if (sharedPreferences != null && !(sharedPreferences.getString("login", "").equals(""))) {
            sharedUserId = sharedPreferences.getString("userid", "");
            sharedLogin = sharedPreferences.getString("login", "");
            sharedEmail = sharedPreferences.getString("email", "");
            sharedPhone = sharedPreferences.getString("phone", "");
        }

        com.example.chronosapp.login.VerifyBackgroundTask backgroundTask = new com.example.chronosapp.login.VerifyBackgroundTask(this);
        backgroundTask.execute(type, sharedLogin, sharedEmail, sharedUserId);

        @SuppressLint("WrongConstant") SharedPreferences sharedPreferences2 = getSharedPreferences("userDataVerCode", MODE_APPEND);
        if (sharedPreferences2 != null && !(sharedPreferences2.getString("key", "").equals(""))) {
            sharedKey = sharedPreferences.getString("key", "");
        }
    }
}