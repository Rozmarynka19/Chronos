package com.example.chronosapp.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

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
import java.nio.charset.StandardCharsets;

public class BackgroundCheckUserTask extends AsyncTask<String, String, String> {
    @SuppressLint("StaticFieldLeak")
    Context context;

    BackgroundCheckUserTask(Context context){
        super();
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String serverAdress = Common.getDbAddress();
        String checkUrl = serverAdress + "checkUser.php";
        String[] params = {"login"};
        String[] paramsValues = {strings[0]};
        try {
            URL url = new URL(checkUrl);
            return postProcedure(url, params, paramsValues);
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
        System.out.println("RESULT: " + s);
    }

    String postProcedure(URL url, String[] params, String[] paramValues){
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            BufferedWriter bufferWriter = new BufferedWriter(outputStreamWriter);
            StringBuilder insert_data = new StringBuilder();
            for(int i=0;i<params.length;i++){
                insert_data.append(URLEncoder.encode(params[i], "UTF-8")).append("=").append(URLEncoder.encode(paramValues[i], "UTF-8"));
                if(i!=params.length-1){
                    insert_data.append("&");
                }
            }
            System.out.println(insert_data);
            bufferWriter.write(insert_data.toString());
            bufferWriter.flush();
            bufferWriter.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1);
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
        return null;
    }
}
