package com.example.chronosapp.ui.itemList;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
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

public class AddTaskBackgroundTask extends AsyncTask<String, String, String> {
    Context context;
    AddTaskBackgroundTaskListener listener;

    AddTaskBackgroundTask(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        //TODO: dates with time - deadline, notificationDate
        //TODO: recurring - list of days in which deadline is set anew
        //[]= {listid, itemname, itemtype, deadline, desc, recurring, notificationDate, piority}
        String plainURL = Common.getDbAddress()+"addTask.php";

        String [] params = {"listid", "itemname", "itemtype", "deadline", "desc", "recurring", "notificationDate", "piority"};
        String [] paramsValues = {strings[0], strings[1], strings[2], strings[3], strings[4], strings[5], strings[6], strings[7]};
        try{
            URL url = new URL(plainURL);
            String result = DataBaseHelper.postProcedure(url, params, paramsValues);
            return result;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d("AddTaskBackgroundTask: ","piority: "+strings[7]);
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        listener = (AddTaskBackgroundTaskListener) context;
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        Log.d("AddTaskBackgroundTask: ","resultMsg: "+s);
        if(s.contains("task added successfully"))
            listener.refreshListOfItems();
        //TODO:
        //if connections problem, print "No connection. Try again later."
        //if problem with posting items (seldom instance), print "Error occured during posting your task"
    }
}
