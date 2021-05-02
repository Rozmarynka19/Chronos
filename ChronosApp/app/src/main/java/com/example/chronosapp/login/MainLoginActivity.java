package com.example.chronosapp.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chronosapp.R;

public class MainLoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView register;
    private Button signButton;
    private EditText editTextLogin, editTextPassword;
    private ImageView passwordShown;

    private Boolean isPasswordShown = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        passwordShown = (ImageView)findViewById(R.id.show_password_image);
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.show_password);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPasswordShown){
                    passwordShown.setImageResource(R.drawable.login_eye);
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }else{
                    passwordShown.setImageResource(R.drawable.login_close_32);
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                isPasswordShown = !isPasswordShown;
            }
        });


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