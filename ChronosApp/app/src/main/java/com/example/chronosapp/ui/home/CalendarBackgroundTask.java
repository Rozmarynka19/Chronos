package com.example.chronosapp.ui.home;

import android.os.AsyncTask;

import com.example.chronosapp.Common;
import com.example.chronosapp.login.DataBaseHelper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class CalendarBackgroundTask extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... strings) {
        if(strings[0].equals("lists")) {
            String plainURL = Common.getDbAddress()+"getLists.php";
            String [] params = {"userid"};
            String [] paramsValues = {strings[1]};
            try{
                URL url = new URL(plainURL);
                String result = DataBaseHelper.postProcedure(url, params, paramsValues);
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        else if(strings[0].equals("items")) {
            String plainURL = Common.getDbAddress()+"getItemsForCalendar.php";
            String[] argsSplitted = strings[1].split(" ");
            String result = "";
            for (String s : argsSplitted) {
                String[] params = {"ids"};
                String[] paramsValues = {s};
                try {
                    URL url = new URL(plainURL);
                    result = result.concat(Objects.requireNonNull(DataBaseHelper.postProcedure(url, params, paramsValues)));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            return result;

        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
