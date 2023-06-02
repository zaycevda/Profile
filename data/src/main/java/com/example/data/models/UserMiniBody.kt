package com.example.data.models

import com.google.gson.annotations.SerializedName

data class UserMiniBody(
    @SerializedName("phone")
    val phone: String,
    @SerializedName("name")
    val name: String, // maxLength = 128
    @SerializedName("username")
    val username: String // minLength = 5, maxLength = 32
)