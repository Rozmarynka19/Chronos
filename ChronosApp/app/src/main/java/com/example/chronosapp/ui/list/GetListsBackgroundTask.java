package com.example.chronosapp.ui.list;

import android.annotation.SuppressLint;
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

public class GetListsBackgroundTask extends AsyncTask<String, String, String>{
    ListFragment context;
    GetListsBackgroundTaskListener listener;

    GetListsBackgroundTask(ListFragment context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String plainURL = Common.getDbAddress()+"getLists.php";
        String userid = strings[0];

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
                String insert_data = URLEncoder.encode("userid", "UTF-8") +"="+URLEncoder.encode(userid, "UTF-8");
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
        Toast.makeText(context.getContext(), s, Toast.LENGTH_SHORT).show();
        listener = (GetListsBackgroundTaskListener) context;
        ArrayList<ListItem> arrayOfLists = new ArrayList<>();

        System.out.println(s);

        String[] separatedOutput = s.split("\n");
        for(int i=0;i<separatedOutput.length;i++)
            System.out.println("i="+String.valueOf(i)+" "+separatedOutput[i]);

        if(separatedOutput[0].equals("connection sucess"))
        {
            int rows = Integer.parseInt(separatedOutput[1]);

            for(int i=0;i<rows*2;i+=2)
                arrayOfLists.add(new ListItem(separatedOutput[i+3],
                        separatedOutput[i+2],
                         context.getResources().getIdentifier("task_list2_layout",
                                                                    "drawable",
                                                                    context.getContext().getPackageName())));
        }
        listener.getLists(arrayOfLists);
    }
}
