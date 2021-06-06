package com.example.chronosapp.ui.home;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chronosapp.MainMainActivity;
import com.example.chronosapp.R;


public class AboutUs extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);

        LinearLayout bkarrow = (LinearLayout)findViewById(R.id.about_us_go_back);
        bkarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView about_us_text = (TextView)findViewById(R.id.about_us_textview);
        about_us_text.setText("We are a group of friends participating in a team project." +
                "\n" +
                "\n" +
                "The applications were created by:"+
                "\n"+
                "Monika Rozmarywnowska\n" +
                "Jakub Kucharski\n" +
                "Olaf Maliszewski\n" +
                "Krzysztof Kubiś\n" +
                "Krzysztof Bieniek\n");
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
