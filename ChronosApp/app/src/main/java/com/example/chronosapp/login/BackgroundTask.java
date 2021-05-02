package com.example.chronosapp.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

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

import static android.content.Context.MODE_PRIVATE;

public class BackgroundTask extends AsyncTask<String, String, String> {
    Context context;

    BackgroundTask(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String type = strings[0];
        String serverAdress = "http://192.168.56.2/chronos/";//"http://192.168.8.105/Example/";
        String loginUrl = serverAdress + "login.php";
        String regUrl = serverAdress + "register.php";
        if(type.equals("reg")){
            String login  = strings[1];
            String password = strings[2];
            String email  = strings[3];
            String phone  = strings[4];
            try{
                URL url = new URL(regUrl);
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                    BufferedWriter bufferWriter = new BufferedWriter(outputStreamWriter);
                    String insert_data = URLEncoder.encode("login", "UTF-8") +"="+URLEncoder.encode(login, "UTF-8")+
                            "&"+URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(password, "UTF-8")+
                            "&"+URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(email, "UTF-8")+
                            "&"+URLEncoder.encode("phone", "UTF-8")+"="+URLEncoder.encode(phone, "UTF-8");
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
        }
        else if(type.equals("login")){
            String login = strings[1];
            String password = strings[2];
            try{
                URL url = new URL(loginUrl);
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                    BufferedWriter bufferWriter = new BufferedWriter(outputStreamWriter);
                    String login_data = URLEncoder.encode("login", "UTF-8") +"="+URLEncoder.encode(login, "UTF-8")+
                            "&"+URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(password, "UTF-8");
                    System.out.println(login_data);
                    bufferWriter.write(login_data);
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
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, s, duration);
        toast.show();

        //Saving user data to sharedPreferences to be able to recognize who's who
        System.out.println(s);
        String[] separatedOutput = s.split("\n");
        for(int i=0;i<separatedOutput.length;i++)
            System.out.println("i="+String.valueOf(i)+separatedOutput[i]);

        if(separatedOutput[0].equals("connection sucess") &&
                (separatedOutput[1].equals("registered succesfully") || separatedOutput[1].equals("login sucesfull")))
        {
            SharedPreferences sharedPreferences = context.getSharedPreferences("userDataSharedPref",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("login",separatedOutput[2]);
            editor.putString("email",separatedOutput[3]);
            editor.putString("phone",separatedOutput[4]);
            editor.apply();
            context.startActivity(new Intent(context, com.example.chronosapp.MainMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            if(context instanceof Activity)
                ((Activity) context).finish();

        }

    }
}
