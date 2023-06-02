package com.example.data.models

import com.google.gson.annotations.SerializedName

data class UserBody(
    @SerializedName("profile_data")
    val profileDataBody: ProfileDataBody
)

data class ProfileDataBody(
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
    val avatar: String? = null,
    @SerializedName("id")
    val id: Long,
    @SerializedName("last")
    val last: String? = null,
    @SerializedName("online")
    val online: Boolean,
    @SerializedName("created")
    val created: String? = null,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("completed_task")
    val completedTask: Long = 0,
    @SerializedName("avatars")
    val avatarsBody: AvatarsBody? = null
)