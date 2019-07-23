package com.possible.demo.features.followers

import com.google.gson.annotations.SerializedName

data class FollowerResponse(
        @SerializedName("login")
        val login: String? = null,

        @SerializedName("avatar_url")
        val avatarUrl: String? = null
)