package com.mobbeel.androidevcctwitter;

import android.util.Log;
import android.widget.ArrayAdapter;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mobbeel.androidevcctwitter.model.Tweet;
import com.mobbeel.androidevcctwitter.model.TwitterSearchResult;
import com.mobbeel.androidevcctwitter.volley.GsonRequest;
import com.mobbeel.androidevcctwitter.volley.VolleyManager;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that implements the requests needed to perform a Twitter Search with the API v1.1
 * https://dev.twitter.com/docs/api/1.1/get/search/tweets
 */
public class TwitterSearch implements Response.Listener<TwitterSearchResult>, Response.ErrorListener, EndlessScrollListener.OnEndReachedListener {

    public static final String QUERY_PARAM = "q";
    /**
     * Token needed by the Authentication API.
     * See https://dev.twitter.com/docs/auth/application-only-auth to learn how this token is got.
     */
    private static final String BEARER_TOKEN = "AAAAAAAAAAAAAAAAAAAAAH6lRgAAAAAA8v5URQhHQ0dEhnHRyQhF17l9Qp8%3DQ2boiUJbVh4NejTVbXUOfTTgKu5VE33PFqIieHq7Q";

    /**
     * URL of the Search API.
     * See https://dev.twitter.com/docs/api/1.1/get/search/tweets
     */
    private static final String SEARCH_URL = "https://api.twitter.com/1.1/search/tweets.json";

    private static final String COUNT_PARAM = "count";

    private static final int DEFAULT_COUNT_VALUE = 25;

    private static final String DEFAULT_QUERY = "AndroiDevCC";

    private ArrayAdapter<Tweet> adapter;

    /**
     * This query is used when the search() method is invoked without a specific query string.
     */
    private String query = DEFAULT_QUERY;

    /**
     * This count value (page size) is used when the search() method is invoked without a specific count value.
     */
    private int count = DEFAULT_COUNT_VALUE;


    public TwitterSearch(ArrayAdapter<Tweet> adapter) {
        this.adapter = adapter;
    }

    public void search() {
        search(this.query, this.count, null);
    }

    public void search(String query) {
        search(query, this.count, null);
    }

    public void search(String query, int count, Long maxId) {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(QUERY_PARAM, query));
        params.add(new BasicNameValuePair(COUNT_PARAM, Integer.toString(count)));

        if (maxId != null) {
            params.add(new BasicNameValuePair("max_id", maxId.toString()));
        }

        VolleyManager.getRequestQueue().add(new GsonRequest<TwitterSearchResult>(SEARCH_URL, params, createTwitterAuthHeaders(), TwitterSearchResult.class, this, this));
    }


    private Map<String, String> createTwitterAuthHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "Bearer " + BEARER_TOKEN);
        return headers;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Log.e(TwitterFeedApp.LOG_TAG, "Error with the Twitter response", volleyError);
    }

    @Override
    public void onResponse(TwitterSearchResult searchResult) {
        Log.d(TwitterFeedApp.LOG_TAG, "Twitter search returned successfully");

        //add tweets to the adapter and notify the changes to refresh the list
        //adapter.addAll(searchResult.getTweets());     //requires API 11

        List<Tweet> tweets = searchResult.getTweets();
        for (Tweet tweet : tweets) {
            adapter.add(tweet);
        }

        adapter.notifyDataSetChanged();
    }

    public String getQuery() {
        return this.query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void onEndReached() {
        //TODO avoid new searches if the last search didn't return any result
        if (adapter.getCount() > 0) {
            //get the id of the last tweet and request tweets older than it with the max_id parameter
            long maxId = (adapter.getItem(adapter.getCount() - 1).getId() - 1);
            search(getQuery(), getCount(), maxId);

        } else {
            search();
        }
    }
}
