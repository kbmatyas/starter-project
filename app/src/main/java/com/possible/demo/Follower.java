package com.possible.demo;

import com.google.gson.annotations.SerializedName;


public class Follower {

    @SerializedName("login")
    public String login;

    @SerializedName("id")
    public String id;

    @SerializedName("avatar_url")
    public String avatarUrl;
}
