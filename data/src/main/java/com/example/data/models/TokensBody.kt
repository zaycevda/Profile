package com.example.data.models

import com.google.gson.annotations.SerializedName

data class TokensBody(
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("user_id")
    val userId: Long
)