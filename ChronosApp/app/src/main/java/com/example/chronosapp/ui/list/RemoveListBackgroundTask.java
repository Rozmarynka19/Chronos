package com.example.chronosapp.ui.list;

import android.annotation.SuppressLint;
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

public class RemoveListBackgroundTask extends AsyncTask<String, String, String>{
    ListFragment context;
    ListItem listItem;
    RecyclerView.ViewHolder viewHolder;
    RemoveListBackgroundTaskListener listener;

    RemoveListBackgroundTask(ListFragment context, RecyclerView.ViewHolder viewHolder, ListItem listItem){
        this.context=context;
        this.listItem = listItem;
        this.viewHolder=viewHolder;
    }

    @Override
    protected String doInBackground(String... strings) {
        String plainURL = Common.getDbAddress()+"removeList.php";
        //String listid = listItem.getListID();

        String [] params = {"listid"};
        String [] paramsValues = {listItem.getListID()};
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
        Toast.makeText(context.getContext(), s, Toast.LENGTH_SHORT).show();
        listener = (RemoveListBackgroundTaskListener) context;
        
        if(s.equals("list removed succesfully\n"))
            listener.removeListFromUI(viewHolder);
        else
            listener.restoreListsFromDb();

    }
}
