package com.example.data.models

import com.google.gson.annotations.SerializedName

data class TokensBody(
    @SerializedName("refresh_token")
    val refreshToken: String? = null,
    @SerializedName("access_token")
    val accessToken: String? = null,
    @SerializedName("user_id")
    val userId: Long? = null
)