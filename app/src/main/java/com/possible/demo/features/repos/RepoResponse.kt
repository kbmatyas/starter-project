package com.possible.demo.features.repos

import com.google.gson.annotations.SerializedName

data class RepoResponse(
        @SerializedName("name")
        val name: String
)