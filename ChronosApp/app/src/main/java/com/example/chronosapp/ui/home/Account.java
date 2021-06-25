package com.example.chronosapp.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chronosapp.MainMainActivity;
import com.example.chronosapp.R;
import com.google.android.gms.vision.text.Line;

public class Account extends AppCompatActivity implements View.OnClickListener {

    String sharedLogin;
    String sharedEmail;
    String sharedPhone;

    final static String unknown_data = "Unknown!";

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

        Button change_password = (Button)findViewById(R.id.change_password);
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), com.example.chronosapp.ui.home.ChangePassword.class);
                finish();
                startActivity(intent);
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

        if(getData(sharedPhone, "Phone number").equals(unknown_data)){
            GoogleLoginSetupFields();
        }
    }

    public String getData(String data, String subtext){
        if (data == "")
            data = unknown_data;

        return subtext + ": " + data;
    }

    public void GoogleLoginSetupFields(){
        LinearLayout acc = (LinearLayout)findViewById(R.id.acc);
        acc.removeView(findViewById(R.id.acc_change_password));
        acc.removeView(findViewById(R.id.acc_additional_options));
        acc.removeView(findViewById(R.id.acc_label1));
        acc.removeView(findViewById(R.id.acc_label2));
        acc.removeView(findViewById(R.id.acc_label3));
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
