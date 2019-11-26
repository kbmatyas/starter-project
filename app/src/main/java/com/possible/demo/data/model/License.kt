package com.possible.demo.data.model

import com.google.gson.annotations.SerializedName

data class License(
    @field:SerializedName("key") val key: String,
    @field:SerializedName("name") val name: String
)