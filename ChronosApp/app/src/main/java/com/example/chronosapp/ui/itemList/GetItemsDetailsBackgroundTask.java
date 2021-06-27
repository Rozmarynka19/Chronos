package com.example.chronosapp.ui.itemList;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.chronosapp.Common;
import com.example.chronosapp.login.DataBaseHelper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetItemsDetailsBackgroundTask extends AsyncTask<String, String, String>{
    Context context;
    GetItemsDetailsBackgroundTaskListener listener;

    GetItemsDetailsBackgroundTask(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String plainURL = Common.getDbAddress()+"getItemsDetails.php";

        String [] params = {"ids"};
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
        ArrayList<Item> mItemArrayList = ((ListOfItemsMainActivity)context).mItemArrayList;

//        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        listener = (GetItemsDetailsBackgroundTaskListener) context;

        Log.d("GetItemsDetailsBackgroundTask - onPostExecute",s);

        /**
         * schema:
         *      1.task {noOfEntities, ID, Deadline, Priority}
         *      2.bill {noOfEntities, ID, Deadline}
         */

        String[] separatedOutput = s.split("\n");
        for(int i=0;i<separatedOutput.length;i++)
            Log.d("i="+String.valueOf(i),separatedOutput[i]);

        if(!separatedOutput[0].equals("connection failed") && !separatedOutput[0].equals("error in request"))
        {
            int rows = Integer.parseInt(separatedOutput[0]),
                counter = 0;

            //tasks part --------------------------------------------
            for(;counter<rows*3;counter+=3)
            {
                for(int i=0;i<mItemArrayList.size();i++)
                    if(mItemArrayList.get(i).getItemID().equals(separatedOutput[counter+1]))
                    {
                        mItemArrayList.get(i).setDeadline(separatedOutput[counter+2]);
                        mItemArrayList.get(i).setPriority(separatedOutput[counter+3]);

                        switch(separatedOutput[counter+3])
                        {
                            case "1":
                                //set bg for low priority task
                                mItemArrayList.get(i).setImageResource(
                                        context.getResources().getIdentifier("task_low_layout",
                                        "drawable",
                                        context.getPackageName()));

                                break;
                            case "2":
                                //set bg for medium priority task
                                mItemArrayList.get(i).setImageResource(
                                        context.getResources().getIdentifier("task_medium_layout",
                                                "drawable",
                                                context.getPackageName()));
                                break;
                            case "3":
                                //set bg for high priority task
                                mItemArrayList.get(i).setImageResource(
                                        context.getResources().getIdentifier("task_high_layout",
                                                "drawable",
                                                context.getPackageName()));
                                break;
                        }
                        break;
                    }
            }

            //bills part --------------------------------------------
            rows = Integer.parseInt(separatedOutput[counter]);
            for(;counter<rows*2;counter+=2)
            {
                for(int i=0;i<mItemArrayList.size();i++)
                    if(mItemArrayList.get(i).getItemID().equals(separatedOutput[counter+1]))
                    {
                        mItemArrayList.get(i).setDeadline(separatedOutput[counter+2]);
                        break;
                    }
            }
        }
        listener.refreshItemsDetails(mItemArrayList);
    }
}
