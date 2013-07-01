package com.mobbeel.androidevcctwitter;

import android.app.ListActivity;
import android.os.Bundle;

/**
 * Main (and only) activity of the demo application.
 * Shows a list of tweets recovered through the Twitter Search API.
 */
public class TwitterFeedActivity extends ListActivity {

    private TweetArrayAdapter tweetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_twitter_feed);

        tweetAdapter = new TweetArrayAdapter(this);
        setListAdapter(tweetAdapter);

        new TwitterSearch(tweetAdapter).search("AndroiDevCC", 50);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}
