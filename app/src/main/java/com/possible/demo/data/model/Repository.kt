package com.possible.demo.data.model

import com.google.gson.annotations.SerializedName

data class Repository(
    @field:SerializedName("id") val id: Long,
    @field:SerializedName("node_id") val nodeId: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("full_name") val fullName: String,
    @field:SerializedName("private") val isPrivate: Boolean,
    @field:SerializedName("description") val description: String? = null,

    @field:SerializedName("created_at") val createdAt: String,
    @field:SerializedName("updated_at") val updatedAt: String,

    @field:SerializedName("stargazers_count") val stargazerCount: Int = 0,
    @field:SerializedName("watchers_count") val watcherCount: Int = 0,
    @field:SerializedName("language") val language: String? = null,

    @SerializedName("license") val license: License? = null
)