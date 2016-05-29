package com.example.josangel.angelhack16_1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.josangel.angelhack16_1.service.PreferenceUtil;
import com.example.josangel.angelhack16_1.service.HttpService;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "eHznSCRVq6rLaweUM1RSNK8sv";
    private static final String TWITTER_SECRET = "5eETl4u7Xm7aKsyv7FKKSScVSfc5DxXY30HDV6UDMfrFNUtjb6";

    private TwitterLoginButton loginButton;
    private static final String TAG = "com.example.josangel";

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig), new Crashlytics());
        setContentView(R.layout.activity_main);

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = result.data;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model
                String msg = "@" + session.getUserName() + " logged in!";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                Log.i(TAG, "success: " + result.data);

                // Save login details locally
                PreferenceUtil.saveToPrefs(MainActivity.this, PreferenceUtil.PREFS_LOGIN_USERNAME_KEY, result.data.getUserName());
                PreferenceUtil.saveToPrefs(MainActivity.this, PreferenceUtil.PREFS_LOGIN_AUTH_TOKEN, result.data.getAuthToken().token);
                PreferenceUtil.saveToPrefs(MainActivity.this, PreferenceUtil.PREFS_LOGIN_USER_ID, result.data.getUserId() + "");

                // Save the data to Server
                new HttpService(MainActivity.this).execute("user/login");

                // Navigate user to map page
                Intent mapIntent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(mapIntent);
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

}
