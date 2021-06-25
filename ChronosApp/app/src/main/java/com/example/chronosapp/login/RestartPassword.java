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

public class RestartPassword extends AppCompatActivity implements View.OnClickListener {

    private EditText email_field;
    private TextView error_field;
    private Button restart_btn;
    private login_error_informations errors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_layout);

        email_field = (EditText) findViewById(R.id.forgotPassword_login);
        error_field = (TextView) findViewById(R.id.forgotPassword_error);
        restart_btn = (Button) findViewById(R.id.forgotPassword_btn);
        restart_btn.setOnClickListener(this);


        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_click_fix);
        LinearLayout back_arrow = (LinearLayout) findViewById(R.id.back_to_login);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RestartPassword.this, MainLoginActivity.class));
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
            case R.id.forgotPassword_btn:
                restart_password();
                break;
        }
    }

    private void set_error(String msg) {
        error_field.setText(msg);
    }


    private void restart_password() {
        String email = email_field.getText().toString().trim();


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_field.setError(errors.invalid_email);
            set_error(errors.invalid_email);
            email_field.requestFocus();
            return;
        }
    }
}