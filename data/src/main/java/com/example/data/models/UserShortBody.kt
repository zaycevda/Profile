package com.example.data.models

import com.google.gson.annotations.SerializedName

data class UserShortBody(
    @SerializedName("name")
    val name: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("birthday")
    val birthday: String? = null,
    @SerializedName("city")
    val city: String? = null,
    @SerializedName("vk")
    val vk: String? = null,
    @SerializedName("instagram")
    val instagram: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("avatar")
    val avatarBody: AvatarBody? = null
)

data class AvatarBody(
    @SerializedName("filename")
    val filename: String,
    @SerializedName("base_64")
    val base64: String
)