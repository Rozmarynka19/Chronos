package com.example.chronosapp.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import static android.content.Context.MODE_PRIVATE;

public class BackgroundTask extends AsyncTask<String, String, String> {
    Context context;

    BackgroundTask(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String type = strings[0];
        String serverAdress = Common.getDbAddress();

        String loginUrl = serverAdress + "login.php";
        String regUrl = serverAdress + "register.php";
        if(type.equals("reg")){
           /* String login  = strings[1];
            String password = strings[2];
            String email  = strings[3];
            String phone  = strings[4];*/
            String [] params = {"login", "password", "email", "phone"};
            String [] paramsValues = {strings[1], strings[2], strings[3], strings[4]};
            try{
                URL url = new URL(regUrl);
                String result = postProcedure(url, params, paramsValues);
                return result;

               /*     HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
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
                }*/

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("login")){
            String login = strings[1];
            String password = strings[2];
            String [] params = {"login", "password"};
            String [] paramsValues = {strings[1], strings[2]};
            try{
                URL url = new URL(loginUrl);
                String result = postProcedure(url, params, paramsValues);
                return result;
                /*try {
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
                }*/
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("GSignUp")){
           /* String login = strings[1];
            String password = strings[2];
            String [] params = {"login", "password"};
            String [] paramsValues = {strings[1], strings[2]};
            try{
                URL url = new URL(loginUrl);
                String result = postProcedure(url, params, paramsValues);
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }*/
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
            editor.putString("userid",separatedOutput[2]);
            editor.putString("login",separatedOutput[3]);
            editor.putString("email",separatedOutput[4]);
            editor.putString("phone",separatedOutput[5]);
            editor.apply();
            context.startActivity(new Intent(context, com.example.chronosapp.MainMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            if(context instanceof Activity)
                ((Activity) context).finish();

        }
    }

    String postProcedure(URL url, String params[], String paramValues[]){
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            BufferedWriter bufferWriter = new BufferedWriter(outputStreamWriter);
            String insert_data = "";
            for(int i=0;i<params.length;i++){
                insert_data+=URLEncoder.encode(params[i], "UTF-8") +"="+URLEncoder.encode(paramValues[i], "UTF-8");
                if(i!=params.length-1){
                    insert_data+="&";
                }
            }
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
        return null;
    }

}
