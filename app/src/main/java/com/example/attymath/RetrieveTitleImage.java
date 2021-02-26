package com.example.attymath;

import android.os.AsyncTask;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

class RetrieveTitleImage extends AsyncTask<String,Integer,Boolean> {

    @Override
    protected Boolean doInBackground(String... strings) {
        String hello = strings[0];
        try {
            URL u = new URL("https://ov12-engine.flamingtext.com/netfu/tmp28004/coollogo_com-218844693.png");
            URLConnection conn = u.openConnection();
            int contentLength = conn.getContentLength();

            DataInputStream stream = new DataInputStream(u.openStream());

            byte[] buffer = new byte[contentLength];
            stream.readFully(buffer);
            stream.close();

            DataOutputStream fos = new DataOutputStream(new FileOutputStream("/sdcard/Android/data/com.example.attymath/files/AttyMath.png"));
            fos.write(buffer);
            fos.flush();
            fos.close();
            return true;
        } catch(FileNotFoundException e) {
            return false; // swallow a 404
        } catch (IOException e) {
            return false; // swallow a 404
        }

    }
}
