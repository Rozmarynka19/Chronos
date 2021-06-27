package com.example.chronosapp.ui.home;

import android.content.Context;
import android.os.AsyncTask;

import com.example.chronosapp.Common;
import com.example.chronosapp.login.DataBaseHelper;

import java.net.MalformedURLException;
import java.net.URL;

public class MyDataBackgroundTask extends AsyncTask<String, String, String> {
    Context context;

    MyDataBackgroundTask(Context context){
        this.context = context;
    }
    @Override
    protected String doInBackground(String... strings) {
        if(strings[0].equals("SAVE")) {
            String plainURL = Common.getDbAddress()+"saveDataAccount.php";
            String [] params = {"User_ID", "Bill_Account_Name", "Bill_Account_Number"};
            String [] paramsValues = {strings[1], strings[2], strings[3]};
            try{
                URL url = new URL(plainURL);
                return DataBaseHelper.postProcedure(url, params, paramsValues);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        else if(strings[0].equals("GET")) {
            String plainURL = Common.getDbAddress()+"getDataAccount.php";
            String [] params = {"User_ID"};
            String [] paramsValues = {strings[1]};
            try{
                URL url = new URL(plainURL);
                return DataBaseHelper.postProcedure(url, params, paramsValues);

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
    }
}
