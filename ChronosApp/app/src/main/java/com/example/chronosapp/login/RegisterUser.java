package com.example.chronosapp.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chronosapp.R;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private TextView registerUser;
    private EditText editTextPhone, editTextEmail, editTextPassword, editTextPassword2, editTextLogin;
    private ProgressBar progressBar;
    private login_error_informations errors;

    private ImageView passwordShown_reg1;
    private ImageView passwordShown_reg2;

    private Boolean isPasswordShown_reg1 = false;
    private Boolean isPasswordShown_reg2 = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        registerUser = (Button) findViewById(R.id.Register);
        registerUser.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.EmailRegistration);
        editTextLogin = (EditText) findViewById(R.id.LoginRegistration);
        editTextPassword = (EditText) findViewById(R.id.PasswordRegistration);
        editTextPassword2 = (EditText) findViewById(R.id.PasswordRegistration2);
        editTextPhone = (EditText) findViewById(R.id.PhoneRegistration);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_click_fix);

        passwordShown_reg1 = (ImageView) findViewById(R.id.show_password_image_reg1);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.show_password_reg1);

        passwordShown_reg2 = (ImageView) findViewById(R.id.show_password_image_reg2);
        RelativeLayout relativeLayout2 = (RelativeLayout) findViewById(R.id.show_password_reg2);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordShown_reg1) {
                    passwordShown_reg1.setImageResource(R.drawable.login_eye);
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    passwordShown_reg1.setImageResource(R.drawable.login_close_32);
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                isPasswordShown_reg1 = !isPasswordShown_reg1;
            }
        });


        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordShown_reg2) {
                    passwordShown_reg2.setImageResource(R.drawable.login_eye);
                    editTextPassword2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    passwordShown_reg2.setImageResource(R.drawable.login_close_32);
                    editTextPassword2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                isPasswordShown_reg2 = !isPasswordShown_reg2;
            }
        });

        LinearLayout back_arrow = (LinearLayout) findViewById(R.id.back_to_login);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterUser.this, com.example.chronosapp.login.MainLoginActivity.class));
            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
        String password2 = (editTextPassword2.getText().toString().trim());
        String phone = (editTextPhone.getText().toString().trim());
        String login = (editTextLogin.getText().toString().trim());

        if (login.isEmpty()) {
            editTextLogin.setError("second name required");
            editTextLogin.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            editTextPhone.setError("Phone required");
            editTextPhone.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Email required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter valid email adress!");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password required");
            editTextPassword.requestFocus();
            return;
        }

        if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[_])(?=\\S+$).{8,}$")) {
            editTextPassword.setError("Password must contain at least 8 characters, one upper and one lower letter and special character '_'");
            editTextPassword.requestFocus();
            return;
        }

        if (!password.equals(password2)) {
            editTextPassword.setError("Passwords do not match!");
            editTextPassword.requestFocus();

            editTextPassword2.setError("Passwords do not match!");
            editTextPassword2.requestFocus();
            return;
        }

        /*if (password.length() < 6) {
            editTextPassword.setError("Min password length is 6");
            editTextPassword.requestFocus();
            return;
        }*/

        progressBar.setVisibility(View.VISIBLE);
        String type = "reg";
//        BackgroundTask backgroundTask = new BackgroundTask(getApplicationContext());
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(type, login, password, email, phone);
        progressBar.setVisibility(View.GONE);

    }
}