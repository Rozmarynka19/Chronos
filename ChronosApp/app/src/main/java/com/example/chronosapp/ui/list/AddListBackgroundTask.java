package com.example.chronosapp.ui.list;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.chronosapp.R;

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
        String plainURL = context.getResources().getString(R.string.localMrServer)+"addList.php";
        String userid = strings[0], listname = strings[1];

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
                String insert_data = URLEncoder.encode("userid", "UTF-8") +"="+URLEncoder.encode(userid, "UTF-8")+
                        "&"+URLEncoder.encode("listname", "UTF-8")+"="+URLEncoder.encode(listname, "UTF-8");
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
    }
}
