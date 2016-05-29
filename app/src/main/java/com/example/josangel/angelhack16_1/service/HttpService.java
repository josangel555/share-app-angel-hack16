package com.example.josangel.angelhack16_1.service;

import android.content.Context;
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
public class HttpService extends AsyncTask<String, String, String> {

    private static final String TAG = "com.example.josangel";
    private Context context;

    public HttpService(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {

        String responseString = null;
        try{

            URL url = new URL("http://192.168.1.94:8080/api/" + params[0]);

            HttpURLConnection con;
            try {
                con = getConnection(params, url);
            } catch(IllegalArgumentException e) {
                return null;
            }

            int status = con.getResponseCode();
            if(status == HttpURLConnection.HTTP_OK){
                Log.i(TAG, "doInBackground: Request Successful : " + params[0]);
                return "SUCCESS";
            } else if(status == HttpURLConnection.HTTP_UNAUTHORIZED){
                Log.i(TAG, "doInBackground: User Auth Failed for request : " + params[0]);
                return "AUTH_FAIL";
            } else {
                Log.w(TAG, "doInBackground: Request Failed : " + params[0]);
                return "FAILURE";
            }

        } catch(MalformedURLException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }

        return "";
    }

    private HttpURLConnection getConnection(String[] params, URL url) throws IOException {

        String loggedInUserName = PreferenceUtil.getFromPrefs(this.context, PreferenceUtil.PREFS_LOGIN_USERNAME_KEY, "");
        String loggedInAuthToken = PreferenceUtil.getFromPrefs(this.context, PreferenceUtil.PREFS_LOGIN_AUTH_TOKEN, "");
        String loggedInUserId = PreferenceUtil.getFromPrefs(this.context, PreferenceUtil.PREFS_LOGIN_USER_ID, "");

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        if(params[0].equals("user/login")){

            if(params.length != 1){
                throw new IllegalArgumentException("expect 1 argument");
            }

            con.setRequestMethod("GET");
            con.setRequestProperty("userName", loggedInUserName);
            con.setRequestProperty("userId", loggedInUserId);
            con.setRequestProperty("authToken", loggedInAuthToken);
            con.setRequestProperty("id", loggedInUserId);

        } else if(params[0].equals("user/update-loc")){

            if(params.length != 2){
                throw new IllegalArgumentException("expect 2 arguments");
            }

            con.setRequestMethod("GET");
            con.setRequestProperty("userName", loggedInUserName);
            con.setRequestProperty("userId", loggedInUserId);
            con.setRequestProperty("authToken", loggedInAuthToken);

            con.setRequestProperty("loc", params[1]);

        } else if(params[0].equals("book/add")){
            if(params.length != 5){
                throw new IllegalArgumentException("expect 5 arguments");
            }

            con.setRequestMethod("GET");
            con.setRequestProperty("userName", loggedInUserName);
            con.setRequestProperty("userId", loggedInUserId);
            con.setRequestProperty("authToken", loggedInAuthToken);

            con.setRequestProperty("bookName", params[1]);
            con.setRequestProperty("bookAuthor", params[2]);
            con.setRequestProperty("bookPublisher", params[3]);
            con.setRequestProperty("bookCost", params[4]);
        }

        return con;
    }
}
