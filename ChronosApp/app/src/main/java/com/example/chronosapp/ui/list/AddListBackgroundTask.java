package com.example.chronosapp.ui.list;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.chronosapp.Common;
import com.example.chronosapp.login.DataBaseHelper;

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

public class AddListBackgroundTask extends AsyncTask<String, String, String> {
    Context context;

    AddListBackgroundTask(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String plainURL = Common.getDbAddress()+"addList.php";
        //String userid = strings[0], listname = strings[1];

        String [] params = {"userid", "listname"};
        String [] paramsValues = {strings[0],  strings[1]};
        try{
            URL url = new URL(plainURL);
            String result = DataBaseHelper.postProcedure(url, params, paramsValues);
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
