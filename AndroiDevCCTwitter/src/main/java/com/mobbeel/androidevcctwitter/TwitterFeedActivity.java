package com.mobbeel.androidevcctwitter;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.ListView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mobbeel.androidevcctwitter.twitter.TweetData;
import com.mobbeel.androidevcctwitter.twitter.TwitterManager;


public class TwitterFeedActivity extends Activity {

    private static final String LOG_TAG = "AndroiDevCCTwitter" ;
    private ListView tweetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RequestManager.init(this);

        setContentView(R.layout.activity_twitter_feed);
        tweetList = (ListView)findViewById(R.id.tweetList);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(tweetList.getAdapter() == null) {
            // Get the first page
            TwitterManager.getInstance().getDefaultHashtagTweets(new Response.Listener<TweetData>() {
                                                                     @Override
                                                                     public void onResponse(TweetData response) {
                                                                         Log.v(LOG_TAG, "Tweet data loaded");
                                                                         tweetList.setAdapter(new TweetsArrayAdapter(TwitterFeedActivity.this, response));
                                                                     }
                                                                 } , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(LOG_TAG, "Tweet data failed to load");
                }
            });
        }


    }

}
