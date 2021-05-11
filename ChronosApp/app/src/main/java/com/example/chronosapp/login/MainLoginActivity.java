package com.example.chronosapp.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chronosapp.R;

public class MainLoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView register;
    private Button signButton;
    private EditText editTextLogin, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        @SuppressLint("WrongConstant")
        SharedPreferences sharedPreferences = getSharedPreferences("userDataSharedPref", MODE_APPEND);
        if(sharedPreferences!=null && !(sharedPreferences.getString("login","").equals("")))
        {
            startActivity(new Intent(this, com.example.chronosapp.MainMainActivity.class));
            this.finish();
        }

        editTextLogin = (EditText) findViewById(R.id.login);
        editTextPassword = (EditText) findViewById(R.id.password);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        signButton = (Button) findViewById(R.id.signIn);
        signButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                startActivity(new Intent(this, com.example.chronosapp.login.RegisterUser.class));
                this.finish();
                break;
            case R.id.signIn:
                login();
                break;
        }
    }



    private void login(){
        String username = (editTextLogin.getText().toString().trim());
        String password = (editTextPassword.getText().toString().trim());
        String type = "login";
//        com.example.chronosapp.login.BackgroundTask backgroundTask = new com.example.chronosapp.login.BackgroundTask(getApplicationContext());
        com.example.chronosapp.login.BackgroundTask backgroundTask = new com.example.chronosapp.login.BackgroundTask(this);
        backgroundTask.execute(type, username, password);
    }
}