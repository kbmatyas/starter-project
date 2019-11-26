package com.possible.demo.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @field:SerializedName("id") val id: Long,
    @field:SerializedName("login") val login: String,
    @field:SerializedName("node_id") val nodeId: String?,
    @field:SerializedName("avatar_url") val avatarUrl: String?
)