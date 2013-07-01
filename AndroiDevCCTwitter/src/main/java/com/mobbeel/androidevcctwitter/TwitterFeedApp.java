package com.mobbeel.androidevcctwitter;

import android.app.Application;
import com.mobbeel.androidevcctwitter.volley.VolleyManager;

/**
 * Class used to initialize the Volley RequestQueue and ImageLoader when
 * the application starts.
 */
public class TwitterFeedApp extends Application{

    public static final String LOG_TAG = "AndroiDevCCTwitter" ;

    @Override
    public void onCreate() {
        super.onCreate();
        VolleyManager.init(this);
    }
}
