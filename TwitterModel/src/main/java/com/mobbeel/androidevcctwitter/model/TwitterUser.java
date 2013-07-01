package com.mobbeel.androidevcctwitter.model;

import com.google.gson.annotations.SerializedName;

/**
 * POJO to store information about a Twitter user.
 * Annotated with JSON annotations where needed to convert automatically from JSON.
 */
public class TwitterUser {

    private String name;

    @SerializedName("screen_name")
    private String username;

    @SerializedName("profile_image_url")
    private String profileImageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
