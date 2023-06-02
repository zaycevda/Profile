package com.example.data.models

import com.google.gson.annotations.SerializedName

data class AuthBody(
    @SerializedName("refresh_token")
    val refreshToken: String? = null,
    @SerializedName("access_token")
    val accessToken: String? = null,
    @SerializedName("user_id")
    val userId: Long? = null,
    @SerializedName("is_user_exists")
    val isUserExists: Boolean
)