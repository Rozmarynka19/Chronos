package com.example.chronosapp.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.chronosapp.Common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class BackgroundTask extends AsyncTask<String, String, String> {
    Context context;

    BackgroundTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String type = strings[0];
        String serverAdress = Common.getDbAddress();

        String loginUrl = serverAdress + "login.php";
        String regUrl = serverAdress + "register.php";
        String regGoogleUrl = serverAdress + "Gregister.php";
        if (type.equals("reg")) {
            String[] params = {"login", "password", "email", "phone"};
            String[] paramsValues = {strings[1], strings[2], strings[3], strings[4]};
            try {
                URL url = new URL(regUrl);
                String result = DataBaseHelper.postProcedure(url, params, paramsValues);
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else if (type.equals("login")) {

            String[] params = {"login", "password"};
            String[] paramsValues = {strings[1], strings[2]};
            try {
                URL url = new URL(loginUrl);
                String result = DataBaseHelper.postProcedure(url, params, paramsValues);
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else if (type.equals("GsignUp")) {
            String[] params = {"login", "email"};
            String[] paramsValues = {strings[1], strings[2]};
            try {
                URL url = new URL(regGoogleUrl);
                String result = DataBaseHelper.postProcedure(url, params, paramsValues);
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;
        System.out.println(s);
        Toast toast = Toast.makeText(context, s, duration);
        toast.show();

        //Saving user data to sharedPreferences to be able to recognize who's who
        System.out.println(s);
        String[] separatedOutput = s.split("\n");

        for (int i = 0; i < separatedOutput.length; i++)
            System.out.println("i=" + String.valueOf(i) + separatedOutput[i]);

        if (separatedOutput[0].equals("connection sucess") &&
                (separatedOutput[1].equals("registered succesfully") || separatedOutput[1].equals("login sucesfull"))) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("userDataSharedPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("userid", separatedOutput[2]);
            editor.putString("login", separatedOutput[3]);
            editor.putString("email", separatedOutput[4]);
            editor.putString("phone", separatedOutput[5]);
            editor.putString("is_verified", separatedOutput[6].substring(0,1));
            editor.apply();
            System.out.println("smieci \n" + separatedOutput[2]);
            String str = "0";
            if (str.compareTo(separatedOutput[6].substring(0,1)) != 0) {
                System.out.println("\nscam moment\n");
                context.startActivity(new Intent(context, com.example.chronosapp.MainMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } else {
                context.startActivity(new Intent(context, com.example.chronosapp.login.VerifyActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
            if (context instanceof Activity)
                ((Activity) context).finish();

        }
        else if (separatedOutput[0].equals("connection sucess") &&
                (separatedOutput[1].equals("gregistered succesfully") || separatedOutput[1].equals("glogin sucesfull"))) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("userDataSharedPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("userid", separatedOutput[2]);
            editor.putString("login", separatedOutput[3]);
            editor.putString("email", separatedOutput[4]);
            editor.apply();
            context.startActivity(new Intent(context, com.example.chronosapp.MainMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            if (context instanceof Activity)
                ((Activity) context).finish();

        }
    }

}
