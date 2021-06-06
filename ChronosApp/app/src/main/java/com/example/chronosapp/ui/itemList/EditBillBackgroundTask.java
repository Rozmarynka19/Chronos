package com.example.chronosapp.ui.itemList;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
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

public class EditBillBackgroundTask extends AsyncTask<String, String, String> {
    Context context;
    EditBillBackgroundTaskListener listener;

    EditBillBackgroundTask(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        //TODO: dates with time - deadline
        //[] = {itemid, itemname, billRecipient, billRecipientBankAccount,
        // billTransferTitle, billAmount, billDesc, billDeadline}
        String plainURL = Common.getDbAddress()+"editBill.php";
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
                        "&"+URLEncoder.encode("billRecipient", "UTF-8")+"="+URLEncoder.encode(strings[2], "UTF-8")+
                        "&"+URLEncoder.encode("billRecipientBankAccount", "UTF-8")+"="+URLEncoder.encode(strings[3], "UTF-8")+
                        "&"+URLEncoder.encode("billTransferTitle", "UTF-8")+"="+URLEncoder.encode(strings[4], "UTF-8")+
                        "&"+URLEncoder.encode("billAmount", "UTF-8")+"="+URLEncoder.encode(strings[5], "UTF-8")+
                        "&"+URLEncoder.encode("billDesc", "UTF-8")+"="+URLEncoder.encode(strings[6], "UTF-8")+
                        "&"+URLEncoder.encode("billDeadline", "UTF-8")+"="+URLEncoder.encode(strings[7], "UTF-8");
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
        listener = (EditBillBackgroundTaskListener) context;
//        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        Log.d("EditBillBackgroundTaskListener: ","resultMsg: "+s);
        if(s.contains("bill updated successfully\n"))
            listener.refreshListOfItems();
        //TODO:
        //if connections problem, print "No connection. Try again later."
        //if problem with posting items (seldom instance), print "Error occured during posting your task"
    }
}
