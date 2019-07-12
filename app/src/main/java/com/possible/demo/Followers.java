package com.possible.demo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Followers {

    @SerializedName("followers")
    public List<Follower> followers;
}
