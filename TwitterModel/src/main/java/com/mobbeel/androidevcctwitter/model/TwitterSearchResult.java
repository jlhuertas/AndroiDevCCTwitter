package com.mobbeel.androidevcctwitter.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * POJO to store the results of a Twitter search.
 * Annotated with GSON where needed to convert automatically from JSON.
 */
public class TwitterSearchResult {

    @SerializedName("statuses")
    private List<Tweet> tweets = new ArrayList<Tweet>();

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }
}
