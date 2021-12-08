package com.example.attymath;

import android.os.AsyncTask;
import android.util.Log;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;


class RetrieveTitleImage extends AsyncTask<String, Integer, Boolean> {
    private static final String TAG = "Retrieve";

    @Override
    protected Boolean doInBackground(String... strings) {


        //inline will store the JSON data streamed in string format
        String inline = "";
        String newURL = "";

        try {
            Log.d(TAG, "Doing Get");
            URL url = new URL(strings[0]);
            // open the connection in order to get the JSON data
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //Set the request to GET or POST as per the requirements
            conn.setRequestMethod("GET");
            //Use the connect method to create the connection bridge
            conn.connect();
            //Get the response status of the Rest API
            int responsecode = conn.getResponseCode();
            System.out.println("Response code is: " + responsecode);

            //Iterating condition to if response code is not 200 then throw a runtime exception
            //else continue the actual process of getting the JSON data
            if (responsecode != 200)
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            else {
                //Scanner functionality will read the JSON data from the stream
                Scanner sc = new Scanner(url.openStream());
                while (sc.hasNext()) {
                    inline += sc.nextLine();
                }
                newURL = inline.split(",")[1];
                newURL = newURL.substring(7, newURL.length() - 1);
                //Close the stream when reading the data has been finished
                sc.close();
            }

            //Disconnect the HttpURLConnection stream
            conn.disconnect();
        } catch (Exception e) {
            Log.d(TAG, " My Big Problem Exception");
            e.printStackTrace();
        }
        try {
            Log.d(TAG, " My doing download");
            URL u = new URL(newURL);
            URLConnection conn = u.openConnection();
            int contentLength = conn.getContentLength();

            DataInputStream stream = new DataInputStream(u.openStream());

            byte[] buffer = new byte[contentLength];
            stream.readFully(buffer);
            stream.close();
//            File tempFile = new File("/sdcard/Android/data/com.example.attymath/AttyMath.png");
//            tempFile.createNewFile();


            DataOutputStream fos = new DataOutputStream(new FileOutputStream(strings[1]));
            fos.write(buffer);
            fos.flush();
            fos.close();
            return true;
        } catch (FileNotFoundException e) {
            Log.d(TAG, " My File not found");
            Log.d(TAG, e.getMessage());
            return false; // swallow a 404
        } catch (IOException e) {
            Log.d(TAG, "My IO Exception  ");
            Log.d(TAG, e.getMessage());
            return false; // swallow a 404
        }

    }
}




