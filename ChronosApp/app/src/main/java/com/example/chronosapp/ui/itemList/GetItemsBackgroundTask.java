package com.example.chronosapp.ui.itemList;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

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

        String [] params = {"listid"};
        String [] paramsValues = {strings[0]};
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

    @Override
    protected void onPostExecute(String s) {
        //Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        listener = (GetItemsBackgroundTaskListener) context;
        ArrayList<Item> arrayOfItems = new ArrayList<>();

        //System.out.println(s);

        String[] separatedOutput = s.split("\n");
        //for(int i=0;i<separatedOutput.length;i++)
            //System.out.println("i="+String.valueOf(i)+" "+separatedOutput[i]);

        if(!separatedOutput[0].equals("connection failed") && !separatedOutput[0].equals("error in request"))
        {
            int rows = Integer.parseInt(separatedOutput[0]);

            for(int i=0;i<rows*3;i+=3)
            {
                if(separatedOutput[i + 3].equals(ItemTypes.Task.toString()))
                {
                    //TODO: set appropriate bg for TASK (piority)
                    arrayOfItems.add(new Item(separatedOutput[i+2],//title
                            separatedOutput[i+1],//itemID
                            separatedOutput[i+3],//type
                            context.getResources().getIdentifier("task_low_layout",
                                    "drawable",
                                    context.getPackageName())));
                }
                else if(separatedOutput[i + 3].equals(ItemTypes.Bill.toString()))
                {
                    //TODO: set appropriate bg for BILL
                    arrayOfItems.add(new Item(separatedOutput[i+2],
                            separatedOutput[i+1],//itemID
                            separatedOutput[i+3],//type
                            context.getResources().getIdentifier("task_low_layout",
                                    "drawable",
                                    context.getPackageName())));
                }
            }
        }
        listener.getLists(arrayOfItems);
    }
}
