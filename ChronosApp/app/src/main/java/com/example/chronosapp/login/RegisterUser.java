package com.example.chronosapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chronosapp.R;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{

    private TextView banner, registerUser;
    private EditText  editTextPhone, editTextEmail, editTextPassword, editTextLogin;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        banner = (TextView) findViewById(R.id.baner);
        banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.Register);
        registerUser.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.EmailRegistration);
        editTextLogin = (EditText) findViewById(R.id.LoginRegistration);
        editTextPassword = (EditText) findViewById(R.id.PasswordRegistration);
        editTextPhone = (EditText) findViewById(R.id.PhoneRegistration);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.baner:
                startActivity(new Intent(this, MainLoginActivity.class));
                break;
            case R.id.Register:
                registerUser();

        }
    }

    private void registerUser() {
        String email = (editTextEmail.getText().toString().trim());
        String password = (editTextPassword.getText().toString().trim());
        String phone = (editTextPhone.getText().toString().trim());
        String login = (editTextLogin.getText().toString().trim());

        if(login.isEmpty()){
            editTextLogin.setError("second name required");
            editTextLogin.requestFocus();
            return;
        }

        if(phone.isEmpty()){
            editTextPhone.setError("Phone required");
            editTextPhone.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editTextEmail.setError("Email required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter valid email adress!");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password required");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextPassword.setError("Min password length is 6");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        String type="reg";
        BackgroundTask backgroundTask = new BackgroundTask(getApplicationContext());
        backgroundTask.execute(type, login, password, email, phone);
        progressBar.setVisibility(View.GONE);

    }
}