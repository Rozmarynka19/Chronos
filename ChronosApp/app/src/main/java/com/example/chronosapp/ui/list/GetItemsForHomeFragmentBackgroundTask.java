package com.example.chronosapp.ui.list;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.chronosapp.Common;
import com.example.chronosapp.login.DataBaseHelper;
import com.example.chronosapp.ui.itemList.GetItemsDetailsBackgroundTaskListener;
import com.example.chronosapp.ui.itemList.Item;
import com.example.chronosapp.ui.itemList.ItemTypes;
import com.example.chronosapp.ui.itemList.ListOfItemsMainActivity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetItemsForHomeFragmentBackgroundTask extends AsyncTask<String, String, String>{
    ListFragment context;
    GetItemsForHomeFragmentBackgroundTaskListener listener;

    GetItemsForHomeFragmentBackgroundTask(ListFragment context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String plainURL = Common.getDbAddress()+"getItemsForHomeFragment.php";
        String listIDs = strings[0];

        String [] params = {"listIDs"};
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
        Log.d("GetItemsForHomeFragmentBackgroundTask",s);

        ArrayList<Item> mItemArrayList = new ArrayList<>();
        listener = (GetItemsForHomeFragmentBackgroundTaskListener) context;

        /**
         * schema:
         *      1.task {noOfEntities, ID, title, Deadline, Priority}
         *      2.bill {noOfEntities, ID, title, Deadline}
         */

        String[] separatedOutput = s.split("\n");
//        for(int i=0;i<separatedOutput.length;i++)
//            Log.d("i="+String.valueOf(i),separatedOutput[i]);

        if(separatedOutput.length<=2 || separatedOutput[0].equals(""))
            return;

        if(!separatedOutput[0].equals("connection failed") && !separatedOutput[0].equals("error in request"))
        {
            int rows = Integer.parseInt(separatedOutput[0]),
                    separatedOutputCounter = 1;

            //tasks part --------------------------------------------
            for(int i=0;i<rows*4;separatedOutputCounter+=4,i+=4)
            {
                Item item = new Item(separatedOutput[separatedOutputCounter+1],
                                        separatedOutput[separatedOutputCounter],
                                        ItemTypes.Task.toString(),
                                        0,
                                        separatedOutput[separatedOutputCounter+2],
                                        separatedOutput[separatedOutputCounter+3]);

                switch(separatedOutput[separatedOutputCounter+3])
                {
                    case "1":
                        //set bg for low priority task
                        item.setImageResource(
                                context.getResources().getIdentifier("task_low_layout",
                                        "drawable",
                                        context.getActivity().getPackageName()));

                        break;
                    case "2":
                        //set bg for medium priority task
                        item.setImageResource(
                                context.getResources().getIdentifier("task_medium_layout",
                                        "drawable",
                                        context.getActivity().getPackageName()));
                        break;
                    case "3":
                        //set bg for high priority task
                        item.setImageResource(
                                context.getResources().getIdentifier("task_high_layout",
                                        "drawable",
                                        context.getActivity().getPackageName()));
                        break;
                }

                mItemArrayList.add(item);
            }

            //bills part --------------------------------------------
            rows = Integer.parseInt(separatedOutput[separatedOutputCounter]);
//            Log.d("rows",String.valueOf(rows));
            for(int i=0;i<rows*3;separatedOutputCounter+=3,i+=3)
            {
                Item item = new Item(separatedOutput[separatedOutputCounter+1],
                                        separatedOutput[separatedOutputCounter],
                                        ItemTypes.Bill.toString(),
                                        context.getResources().getIdentifier("bill_layout_final",
                                                "drawable",
                                                context.getActivity().getPackageName()),
                                        separatedOutput[separatedOutputCounter+2],
                                        "0");
                mItemArrayList.add(item);
            }
        }

//        Log.d("GetItemsForHomeFragmentBackgroundTask - onPostExecute","----------");
//        for (Item item: mItemArrayList) {
//            Log.d("GetItemsForHomeFragmentBackgroundTask - onPostExecute - id",item.getItemID());
//            Log.d("GetItemsForHomeFragmentBackgroundTask - onPostExecute - title",item.getTitle());
//            Log.d("GetItemsForHomeFragmentBackgroundTask - onPostExecute - type",item.getType());
//            Log.d("GetItemsForHomeFragmentBackgroundTask - onPostExecute - deadline",item.getDeadline());
//            Log.d("GetItemsForHomeFragmentBackgroundTask - onPostExecute - priority",item.getPriority());
//        }

        listener.sendItemsToHomeFragment(mItemArrayList);
    }
}
