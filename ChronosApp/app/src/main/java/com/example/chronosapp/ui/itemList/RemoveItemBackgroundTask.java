package com.example.chronosapp.ui.itemList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

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

        try{
            URL url = new URL(plainURL);
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                BufferedWriter bufferWriter = new BufferedWriter(outputStreamWriter);
                String insert_data = URLEncoder.encode("itemid", "UTF-8") +"="+URLEncoder.encode(item.getItemID(), "UTF-8");
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

    @SuppressLint("ResourceType")
    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        listener = (RemoveItemBackgroundTaskListener) context;

        if(s.equals("task removed successfully"))
            listener.removeListFromUI(viewHolder);
        else
            listener.restoreListsFromDb();

    }
}
