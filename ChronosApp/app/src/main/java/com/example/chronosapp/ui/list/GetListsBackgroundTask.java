package com.example.chronosapp.ui.list;

import android.annotation.SuppressLint;
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

        String [] params = {"userid"};
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
