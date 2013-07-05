package com.mobbeel.androidevcctwitter;

import android.app.ListActivity;
import android.os.Bundle;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Window;

/**
 * Main (and only) activity of the demo application.
 * Shows a list of tweets recovered through the Twitter Search API.
 */
public class TwitterFeedActivity extends SherlockListActivity implements SearchListener {

    public static final String DEFAULT_QUERY = "AndroiDevCC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Sherlock_Light);
        super.onCreate(savedInstanceState);

        //This has to be called before setContentView and you must use the
        //class in com.actionbarsherlock.view and NOT android.view
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.activity_twitter_feed);

        //create the adapter that will feed the list through the Twitter API calls
        TweetArrayAdapter tweetAdapter = new TweetArrayAdapter(this);
        setListAdapter(tweetAdapter);

        TwitterSearch twitterSearch = new TwitterSearch(tweetAdapter);
        twitterSearch.setQuery(DEFAULT_QUERY);
        twitterSearch.setSearchListener(this);

        //listener to request more tweets when the user is near the end of the list
        EndlessScrollListener endlessScrollListener = new EndlessScrollListener();
        endlessScrollListener.setOnEndReachedListener(twitterSearch);

        getListView().setOnScrollListener(endlessScrollListener);

        //launch the first search
        twitterSearch.search();


    }

    @Override
    public void onSearchStarted() {
        setSupportProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void onSearchEnded() {
        setSupportProgressBarIndeterminateVisibility(false);
    }
}
