package com.example.chronosapp.ui.home;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chronosapp.MainMainActivity;
import com.example.chronosapp.R;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener {

    EditText change_password_login;
    EditText change_password_password;
    EditText change_password_new_password_1;
    EditText change_password_new_password_2;

    Button change_password_button;


    private Boolean show_password_1 = false;
    private Boolean show_password_2 = false;
    private Boolean show_password_3 = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        LinearLayout change_password_back = (LinearLayout)findViewById(R.id.change_password_back_arrow);

        RelativeLayout relativeLayout1 = (RelativeLayout)findViewById(R.id.change_password_show_r_1);
        RelativeLayout relativeLayout2 = (RelativeLayout)findViewById(R.id.change_password_show_r_2);
        RelativeLayout relativeLayout3 = (RelativeLayout)findViewById(R.id.change_password_show_r_3);

        ImageView imageView1 = (ImageView)findViewById(R.id.change_password_show_1);
        ImageView imageView2 = (ImageView)findViewById(R.id.change_password_show_2);
        ImageView imageView3 = (ImageView)findViewById(R.id.change_password_show_3);

        change_password_login = (EditText)findViewById(R.id.change_password_login);
        change_password_password = (EditText)findViewById(R.id.change_password_password);
        change_password_new_password_1 = (EditText)findViewById(R.id.change_password_new_password_1);
        change_password_new_password_2 = (EditText)findViewById(R.id.change_password_new_password_2);

        change_password_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show_password_1) {
                    imageView1.setImageResource(R.drawable.login_eye);
                    change_password_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    imageView1.setImageResource(R.drawable.login_close_32);
                    change_password_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                show_password_1 = !show_password_1;
            }
        });
        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show_password_2) {
                    imageView2.setImageResource(R.drawable.login_eye);
                    change_password_new_password_1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    imageView2.setImageResource(R.drawable.login_close_32);
                    change_password_new_password_1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                show_password_2 = !show_password_2;
            }
        });
        relativeLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show_password_3) {
                    imageView3.setImageResource(R.drawable.login_eye);
                    change_password_new_password_2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    imageView3.setImageResource(R.drawable.login_close_32);
                    change_password_new_password_2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                show_password_3 = !show_password_3;
            }
        });
//        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            editTextEmail.setError("Please enter valid email adress!");
//            editTextEmail.requestFocus();
//            return;
//        }
        change_password_button = (Button)findViewById(R.id.change_password_button);
        change_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    public void changePassword(){
        String email = change_password_login.getText().toString().trim();
        String old_password = change_password_password.getText().toString().trim();

        String new_password_1 = change_password_new_password_1.getText().toString().trim();
        String new_password_2 = change_password_new_password_2.getText().toString().trim();


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            change_password_login.setError("Please enter valid email adress!");
            change_password_login.requestFocus();
            return;
        }

        if(matchPassword(old_password)){
            change_password_password.setError("Please enter valid password!");
            change_password_password.requestFocus();
            return;
        }

        if(matchPassword(new_password_1)){
            change_password_new_password_1.setError("Please enter valid password!");
            change_password_new_password_1.requestFocus();
            return;
        }

        if(matchPassword(new_password_2)){
            change_password_new_password_2.setError("Please enter valid password!");
            change_password_new_password_2.requestFocus();
            return;
        }

        if(!new_password_1.equals(new_password_2)){
            change_password_new_password_2.setError("Please enter matching passwords!");
            change_password_new_password_2.requestFocus();
            return;
        }
    }

    public boolean matchPassword(String password){
        if(password.matches("\"^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[_])(?=\\\\S+$).{8,}$\""))
            return true;

        return false;
    }


    @Override
    public void onClick(View v) {

    }
    @Override
    public void onBackPressed(){
        startActivity( new Intent(this, Account.class) );
        finish();
    }

}
