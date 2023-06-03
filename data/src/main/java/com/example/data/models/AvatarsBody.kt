package com.example.data.models

import com.google.gson.annotations.SerializedName

data class AvatarsBody(
    @SerializedName("avatar")
    val avatar: String? = null,
    @SerializedName("bigAvatar")
    val bigAvatar: String,
    @SerializedName("miniAvatar")
    val miniAvatar: String
)

data class AvatarsObjectBody(
    @SerializedName("avatars")
    val avatarsBody: AvatarsBody
)