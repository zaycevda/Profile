package com.example.data.models

import com.google.gson.annotations.SerializedName

data class CheckAuthBody(
    @SerializedName("phone")
    val phoneNumber: String, // max length: 30
    @SerializedName("code")
    val code: String
)