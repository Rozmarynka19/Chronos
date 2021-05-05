package com.example.chronosapp.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chronosapp.R;

public class MainLoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView register;
    private Button signButton;
    private EditText editTextLogin, editTextPassword;
    private ImageView passwordShown;
    private TextView login_error_message;
    private login_error_informations errors;


    private Boolean isPasswordShown = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        login_error_message = findViewById(R.id.login_error_message);
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

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.layout_click_fix);


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
            }
        });

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


    private String login_error(String userName, String password){
        if(userName.isEmpty() && password.isEmpty())
            return errors.data_not_provided;

        if(userName.isEmpty())
            return errors.login_not_provided;

        if(password.isEmpty())
            return errors.password_not_provided;

        if(!userName.contains("@") || !userName.contains("."))
            return errors.invalid_email;

        return "";
    }



    private void login(){
        String username = (editTextLogin.getText().toString().trim());
        String password = (editTextPassword.getText().toString().trim());

        String msg = login_error(username, password);

        login_error_message.setText(msg);
        if(!msg.isEmpty())
            return;

        String type = "login";
//        com.example.chronosapp.login.BackgroundTask backgroundTask = new com.example.chronosapp.login.BackgroundTask(getApplicationContext());
        com.example.chronosapp.login.BackgroundTask backgroundTask = new com.example.chronosapp.login.BackgroundTask(this);
        backgroundTask.execute(type, username, password);
    }
}