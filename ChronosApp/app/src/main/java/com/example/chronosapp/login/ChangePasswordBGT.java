package com.example.chronosapp.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.chronosapp.Common;

import java.net.MalformedURLException;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;

public class ChangePasswordBGT  extends AsyncTask<String, String, String> {

    Context context;

    public ChangePasswordBGT(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String type = strings[0];
        String serverAdress = Common.getDbAddress();

        String changePasswordURL = serverAdress + "changePassword.php";
        if (type.equals("change")) {
            String[] params = {"login", "password", "password_new"};
            String[] paramsValues = {strings[1], strings[2], strings[3]};
            try {
                URL url = new URL(changePasswordURL);
                String result = DataBaseHelper.postProcedure(url, params, paramsValues);
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;
        System.out.println(s);
        Toast toast = Toast.makeText(context, s, duration);
        toast.show();

    }
}
