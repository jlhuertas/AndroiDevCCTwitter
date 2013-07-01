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
public class TwitterSearch implements Response.Listener<TwitterSearchResult>, Response.ErrorListener {

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

    private static final int DEFAULT_COUNT_VALUE = 15;

    private ArrayAdapter<Tweet> adapter;

    public TwitterSearch(ArrayAdapter<Tweet> adapter) {
        this.adapter = adapter;
    }

    public void search(String query) {
        search(query, DEFAULT_COUNT_VALUE);
    }

    public void search(String query, int count) {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(QUERY_PARAM, query));
        params.add(new BasicNameValuePair(COUNT_PARAM, Integer.toString(count)));

        VolleyManager.getRequestQueue().add(new GsonRequest<TwitterSearchResult>(SEARCH_URL, params, createTwitterAuthHeaders(), TwitterSearchResult.class, this, this));
    }


    public Map<String, String> createTwitterAuthHeaders() {
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
}
