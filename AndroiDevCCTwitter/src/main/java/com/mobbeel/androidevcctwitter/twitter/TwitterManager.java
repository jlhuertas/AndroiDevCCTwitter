package com.mobbeel.androidevcctwitter.twitter;

import android.net.Uri;
import android.util.Log;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.mobbeel.androidevcctwitter.GsonRequest;
import com.mobbeel.androidevcctwitter.RequestManager;

public class TwitterManager {

	private final String TAG = getClass().getSimpleName();
	private static TwitterManager mInstance;

	private static String TWITTER_BASE = "hhttps://api.twitter.com/1.1/search/tweets.json?q=AndroiDevCC";
	private static String TWITTER_DEFAULT_SEARCH_TERM = "AndroiDevCC";
	private static String TWITTER_QUERY = "q";


	public static TwitterManager getInstance(){
		if(mInstance == null) {
			mInstance = new TwitterManager();
		}

		return mInstance;
	}

	public <T> void getDefaultHashtagTweets(Listener<TweetData> listener, ErrorListener errorListener){
		getTweetForHashtag(listener, errorListener, TWITTER_DEFAULT_SEARCH_TERM);
	}

	public void getTweetForHashtag(Listener<TweetData> listener, ErrorListener errorListener,  String hashtag){

		Uri.Builder uriBuilder = Uri.parse(TWITTER_BASE).buildUpon()
				.appendQueryParameter(TWITTER_QUERY, hashtag);


		String uri = uriBuilder.build().toString();
		Log.i(TAG, "getTweetForHashtag: uri = " + uri);

		GsonRequest<TweetData> request = new GsonRequest<TweetData>(Method.GET
				, uri
				, TweetData.class
				, listener
				, errorListener);

		Log.v(TAG, request.toString());
		RequestManager.getRequestQueue().add(request);
	}

}
