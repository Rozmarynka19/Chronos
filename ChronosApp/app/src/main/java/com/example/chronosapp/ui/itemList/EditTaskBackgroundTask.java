package com.example.chronosapp.ui.itemList;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.chronosapp.Common;
import com.example.chronosapp.ui.list.EditListDialogListener;

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

public class EditTaskBackgroundTask extends AsyncTask<String, String, String> {
    Context context;
    EditTaskBackgroundTaskListener listener;

    EditTaskBackgroundTask(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        //TODO: dates with time - deadline, notificationDate
        //TODO: recurring - list of days in which deadline is set anew
        //[]= {itemid, itemname, itemtype, deadline, desc, recurring, notificationDate, piority}
        String plainURL = Common.getDbAddress()+"editTask.php";
        Log.d("EditTaskBackgroundTask: ","piority: "+strings[7]);
        try{
            URL url = new URL(plainURL);
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                BufferedWriter bufferWriter = new BufferedWriter(outputStreamWriter);
                String insert_data = URLEncoder.encode("itemid", "UTF-8") +"="+URLEncoder.encode(strings[0], "UTF-8")+
                        "&"+URLEncoder.encode("itemname", "UTF-8")+"="+URLEncoder.encode(strings[1], "UTF-8")+
                        "&"+URLEncoder.encode("itemtype", "UTF-8")+"="+URLEncoder.encode(strings[2], "UTF-8")+
                        "&"+URLEncoder.encode("deadline", "UTF-8")+"="+URLEncoder.encode(strings[3], "UTF-8")+
                        "&"+URLEncoder.encode("desc", "UTF-8")+"="+URLEncoder.encode(strings[4], "UTF-8")+
                        "&"+URLEncoder.encode("recurring", "UTF-8")+"="+URLEncoder.encode(strings[5], "UTF-8")+
                        "&"+URLEncoder.encode("notificationDate", "UTF-8")+"="+URLEncoder.encode(strings[6], "UTF-8")+
                        "&"+URLEncoder.encode("piority", "UTF-8")+"="+URLEncoder.encode(strings[7], "UTF-8");
                System.out.println(insert_data);
                bufferWriter.write(insert_data);
                bufferWriter.flush();
                bufferWriter.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "ISO-8859-1");
                BufferedReader bufferReader = new BufferedReader(inputStreamReader);
                String result= "";
                String line = "";
                StringBuilder stringBuilder = new StringBuilder();
                while((line=bufferReader.readLine())!=null){
                    stringBuilder.append(line).append("\n");
                }
                result=stringBuilder.toString();
                bufferReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        listener = (EditTaskBackgroundTaskListener) context;
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        Log.d("EditTaskBackgroundTask: ","resultMsg: "+s);
        if(s.equals("task updated successfully\n"))
            listener.refreshListOfItems();
        //TODO:
        //if connections problem, print "No connection. Try again later."
        //if problem with posting items (seldom instance), print "Error occured during posting your task"
    }
}
