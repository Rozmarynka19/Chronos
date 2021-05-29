package com.example.chronosapp.ui.itemList;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
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
import java.util.Hashtable;
import java.util.List;

public class GetBillBackgroundTask extends AsyncTask<String, String, String> {
    Context context;
    GetBillBackgroundTaskListener listener;

    GetBillBackgroundTask(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String plainURL = Common.getDbAddress()+"getBill.php";
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
                String insert_data = URLEncoder.encode("itemid", "UTF-8") +"="+URLEncoder.encode(strings[0], "UTF-8");
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
        Log.d("GetBillBackgroundTask - onPostExecute - s:",s);
        listener = (GetBillBackgroundTaskListener) context;
        Hashtable<String,String> tableOfBillDetails = new Hashtable<>();
        ArrayList<String> listOfKeys = new ArrayList<>(List.of("Bill_Recipient",
                "Bill_RecipientsBankAccount",
                "Bill_TransferTitle",
                "Bill_Amount",
                "Bill_Desc",
                "Bill_Deadline"));
        System.out.println(s);

        String[] separatedOutput = s.split("\n");
        for(int i=0;i<separatedOutput.length;i++)
            System.out.println("i="+String.valueOf(i)+" "+separatedOutput[i]);

        if(!separatedOutput[0].equals("connection failed") && !separatedOutput[0].equals("error in request"))
            for(int i=0;i<listOfKeys.size();i++)
            {
                Log.d("GetBillBackgroundTask - onPostExecute - i:",String.valueOf(i));
                Log.d("GetBillBackgroundTask - onPostExecute - listOfKeys:",listOfKeys.get(i));
                Log.d("GetBillBackgroundTask - onPostExecute - separatedOutput:",separatedOutput[i+1]);
                tableOfBillDetails.put(listOfKeys.get(i),separatedOutput[i+1]);
            }

        listener.getBillDetails(tableOfBillDetails);
    }
}
