package com.example.chronosapp.login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class DataBaseHelper {
    public static String postProcedure(URL url, String params[], String paramValues[]) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            BufferedWriter bufferWriter = new BufferedWriter(outputStreamWriter);
            String insert_data = "";
            for (int i = 0; i < params.length; i++) {
                insert_data += URLEncoder.encode(params[i], "UTF-8") + "=" + URLEncoder.encode(paramValues[i], "UTF-8");
                if (i != params.length - 1) {
                    insert_data += "&";
                }
            }
            System.out.println(insert_data);
            bufferWriter.write(insert_data);
            bufferWriter.flush();
            bufferWriter.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "ISO-8859-1");
            BufferedReader bufferReader = new BufferedReader(inputStreamReader);
            String result = "";
            String line = "";
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            result = stringBuilder.toString();
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
