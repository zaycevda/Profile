package com.example.data.models

import com.google.gson.annotations.SerializedName

data class UserShortBody(
    @SerializedName("name")
    val name: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("vk")
    val vk: String,
    @SerializedName("instagram")
    val instagram: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("avatar")
    val avatarBody: AvatarBody
)

data class AvatarBody(
    @SerializedName("filename")
    val filename: String,
    @SerializedName("base_64")
    val base64: String
)