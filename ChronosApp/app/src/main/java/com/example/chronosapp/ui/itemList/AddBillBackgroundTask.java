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

public class AddBillBackgroundTask extends AsyncTask<String, String, String> {
    Context context;
    AddBillBackgroundTaskListener listener;

    AddBillBackgroundTask(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        //TODO: dates with time - deadline
        //[] = {listid, itemname, itemtype, billRecipient, billRecipientBankAccount,
        // billTransferTitle, billAmount, billDesc, billDeadline}

        String plainURL = Common.getDbAddress()+"addBill.php";
        String [] params = {"listid", "itemname", "itemtype", "billRecipient", "billRecipientBankAccount", "billTransferTitle", "billAmount", "billDesc", "billDeadline"};
        String [] paramsValues = {strings[0], strings[1], strings[2], strings[3], strings[4], strings[5], strings[6], strings[7], strings[8]};
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
        listener = (AddBillBackgroundTaskListener) context;
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        Log.d("AddBillBackgroundTask: ","resultMsg: "+s);
        if(s.equals("bill added successfully\n"))
            listener.refreshListOfItems();
        //TODO:
        //if connections problem, print "No connection. Try again later."
        //if problem with posting items (seldom instance), print "Error occured during posting your task"
    }
}
