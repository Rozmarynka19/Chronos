package com.example.chronosapp.ui.itemList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

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

public class RemoveItemBackgroundTask extends AsyncTask<String, String, String>{
    Context context;
    Item item;
    RecyclerView.ViewHolder viewHolder;
    RemoveItemBackgroundTaskListener listener;

    RemoveItemBackgroundTask(Context context, RecyclerView.ViewHolder viewHolder, Item item){
        this.context=context;
        this.item = item;
        this.viewHolder=viewHolder;
    }

    @Override
    protected String doInBackground(String... strings) {
        String plainURL = Common.getDbAddress()+"removeItem.php";

        String [] params = {"itemid"};
        String [] paramsValues = {item.getItemID()};
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

    @SuppressLint("ResourceType")
    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        listener = (RemoveItemBackgroundTaskListener) context;

        if(s.equals("item removed successfully"))
            listener.removeListFromUI(viewHolder);
        else
            listener.restoreListsFromDb();

    }
}
