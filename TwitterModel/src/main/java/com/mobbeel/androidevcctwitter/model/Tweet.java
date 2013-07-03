package com.mobbeel.androidevcctwitter.model;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Object to store the relevant information about a Tweet.
 * Annotated with GSON where needed to automatically parse the JSON received from the Twitter servers.
 */
public class Tweet {

    private final static String TWEET_DATE_FORMAT = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";

    private static final SimpleDateFormat inputDateFormat = new SimpleDateFormat(TWEET_DATE_FORMAT, Locale.ENGLISH);

    private static final SimpleDateFormat displayDateFormat = new SimpleDateFormat("d/M/yy H:mm");


    static {
        inputDateFormat.setLenient(true);
        displayDateFormat.setLenient(true);
    }

    private String text;

    private TwitterUser user;

    private long id;

    @SerializedName("created_at")
    private String time;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TwitterUser getUser() {
        return user;
    }

    public void setUser(TwitterUser user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFormatedTime() {
        String formatedDate = "";
        try {
            formatedDate = displayDateFormat.format(inputDateFormat.parse(getTime()));
        } catch (ParseException e) {

        }
        return formatedDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
