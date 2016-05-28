package com.example.josangel.angelhack16_1.service;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by josangel on 5/28/16.
 */
public class RegisterService extends AsyncTask<String, String, String> {

    private static final String TAG = "com.example.josangel";

    @Override
    protected String doInBackground(String... params) {

        if(params.length != 4){
            return null;
        }

        String responseString = null;
        try{
            URL url = new URL("http://localhost:8080/api/user/login");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("userId", params[0]);
            con.setRequestProperty("userName", params[1]);
            con.setRequestProperty("authToken", params[2]);
            con.setRequestProperty("id", params[3]);

            int status = con.getResponseCode();
            if(status == HttpURLConnection.HTTP_OK){
                Log.i(TAG, "doInBackground: User Logged In");
                return "SUCCESS";
            } else {
                Log.w(TAG, "doInBackground: User Login Failed");
                return "FAILURE";
            }

        } catch(MalformedURLException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }

        return "";
    }
}
