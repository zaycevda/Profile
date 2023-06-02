package com.example.data.models

import com.google.gson.annotations.SerializedName

data class AvatarsBody(
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("big_avatar")
    val bigAvatar: String,
    @SerializedName("mini_avatar")
    val miniAvatar: String
)

data class AvatarsObjectBody(
    @SerializedName("avatars")
    val avatarsBody: AvatarsBody
)