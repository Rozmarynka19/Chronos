package com.example.chronosapp.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.SystemClock;
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

import static android.content.Context.MODE_PRIVATE;

public class VerifyBackgroundTask extends AsyncTask<String, String, String> {
    Context context;

    VerifyBackgroundTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String type = strings[0];
        //String serverAdress = "http://algolearn-team.prv.pl/1213146_fsa523/";
        String serverAdress = Common.getDbAddress();
        String emailUrl = serverAdress + "sendEmail.php";
        String verifyUrl = serverAdress + "acceptVerification.php";
        if (type.equals("email")) {
            String[] params = {"login", "email", "id"};
            String[] paramsValues = {strings[1], strings[2], strings[3]};
            try {
                URL url = new URL(emailUrl);
                String result = DataBaseHelper.postProcedure(url, params, paramsValues);
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        else if (type.equals("setVerified")) {
            SystemClock.sleep(5000);
            System.out.println("ver test 1");
            String[] params = {"login", "is_verified", "code"};
            String[] paramsValues = {strings[1], strings[2], strings[3]};
            try {
                URL url = new URL(verifyUrl);
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

        Toast toast = Toast.makeText(context, s, duration);

        toast.show();

        //Saving user data to sharedPreferences to be able to recognize who's who
        System.out.println(s);
        String[] separatedOutput = s.split("\n");
        for (int i = 0; i < separatedOutput.length; i++)
            System.out.println("i=" + String.valueOf(i) + separatedOutput[i]);

        if (separatedOutput[0].equals("connection sucess") && (separatedOutput[1].equals("code succ"))) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("userDataVerCode", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("key", separatedOutput[2]);
            editor.apply();
            System.out.println("key test:  \n" + separatedOutput[2]);
            VerifyActivity.code = separatedOutput[2].substring(0, 4);

            SharedPreferences sharedPreferencesUser = context.getSharedPreferences("userDataSharedPref", MODE_PRIVATE);
            SharedPreferences.Editor editorUser = sharedPreferencesUser.edit();
            editorUser.putString("is_verified", "1");
            editorUser.apply();
            //context.startActivity(new Intent(context, com.example.chronosapp.MainMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

        }
    }

}
