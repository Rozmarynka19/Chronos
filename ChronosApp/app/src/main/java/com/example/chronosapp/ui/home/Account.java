package com.example.chronosapp.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chronosapp.MainMainActivity;
import com.example.chronosapp.R;

public class Account extends AppCompatActivity implements View.OnClickListener {

    String sharedLogin;
    String sharedEmail;
    String sharedPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);

        LinearLayout account_back = (LinearLayout)findViewById(R.id.account_go_back);
        account_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        @SuppressLint("WrongConstant")
        SharedPreferences sharedPreferences = getSharedPreferences("userDataSharedPref", MODE_APPEND);
        if(sharedPreferences!=null && !(sharedPreferences.getString("login","").equals("")))
        {
            sharedLogin = sharedPreferences.getString("login","");
            sharedEmail = sharedPreferences.getString("email","");
            sharedPhone = sharedPreferences.getString("phone","");
        }

        TextView login_text = (TextView)findViewById(R.id.account_login);
        TextView email_text = (TextView)findViewById(R.id.account_email);
        TextView phone_text = (TextView)findViewById(R.id.account_phone);

        login_text.setText(getData(sharedLogin, "Login"));
        email_text.setText(getData(sharedEmail, "E-mail"));
        phone_text.setText(getData(sharedPhone, "Phone number"));
    }

    public String getData(String data, String subtext){
        if (data == "")
            data = "Unknown!";

        return subtext + ": " + data;
    }

    @Override
    public void onClick(View v) {

    }
    @Override
    public void onBackPressed(){
        startActivity( new Intent(this, MainMainActivity.class) );
        finish();
    }
}
