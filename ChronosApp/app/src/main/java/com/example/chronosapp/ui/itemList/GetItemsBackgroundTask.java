package com.example.chronosapp.ui.itemList;

import android.content.Context;
import android.os.AsyncTask;
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
import java.util.ArrayList;

public class GetItemsBackgroundTask extends AsyncTask<String, String, String>{
    Context context;
    GetItemsBackgroundTaskListener listener;

    GetItemsBackgroundTask(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String plainURL = Common.getDbAddress()+"getItems.php";

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
                String insert_data = URLEncoder.encode("listid", "UTF-8") +"="+URLEncoder.encode(strings[0], "UTF-8");
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
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        listener = (GetItemsBackgroundTaskListener) context;
        ArrayList<Item> arrayOfItems = new ArrayList<>();

        System.out.println(s);

        String[] separatedOutput = s.split("\n");
        for(int i=0;i<separatedOutput.length;i++)
            System.out.println("i="+String.valueOf(i)+" "+separatedOutput[i]);

        if(!separatedOutput[0].equals("connection failed") && !separatedOutput[0].equals("error in request"))
        {
            int rows = Integer.parseInt(separatedOutput[0]);

            for(int i=0;i<rows*3;i+=3)
            {
                if(separatedOutput[i + 3].equals(ItemTypes.Task.toString()))
                {
                    //TODO: set appropriate bg for TASK (piority)
                    arrayOfItems.add(new Item(separatedOutput[i+2],
                            separatedOutput[i+1],
                            separatedOutput[i+3],
                            context.getResources().getIdentifier("task_low_layout",
                                    "drawable",
                                    context.getPackageName())));
                }
                else if(separatedOutput[i + 3].equals(ItemTypes.Bill.toString()))
                {
                    //TODO: set appropriate bg for BILL
                    arrayOfItems.add(new Item(separatedOutput[i+2],
                            separatedOutput[i+1],
                            separatedOutput[i+3],
                            context.getResources().getIdentifier("bill_layout_final",
                                    "drawable",
                                    context.getPackageName())));
                }
            }
        }
        listener.getLists(arrayOfItems);
    }
}
