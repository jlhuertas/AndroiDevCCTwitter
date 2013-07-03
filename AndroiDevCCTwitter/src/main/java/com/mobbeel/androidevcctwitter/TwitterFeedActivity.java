package com.mobbeel.androidevcctwitter;

import android.app.ListActivity;
import android.os.Bundle;

/**
 * Main (and only) activity of the demo application.
 * Shows a list of tweets recovered through the Twitter Search API.
 */
public class TwitterFeedActivity extends ListActivity {

    public static final String DEFAULT_QUERY = "AndroiDevCC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_twitter_feed);

        TweetArrayAdapter tweetAdapter = new TweetArrayAdapter(this);
        setListAdapter(tweetAdapter);

        TwitterSearch twitterSearch = new TwitterSearch(tweetAdapter);
        twitterSearch.setQuery(DEFAULT_QUERY);

        //listener to request more tweets when the user is near the end of the list
        EndlessScrollListener endlessScrollListener = new EndlessScrollListener();
        endlessScrollListener.setOnEndReachedListener(twitterSearch);

        getListView().setOnScrollListener(endlessScrollListener);

        //launch the first search
        twitterSearch.search();

    }

}
