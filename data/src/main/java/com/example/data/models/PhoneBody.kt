package com.example.data.models

import com.google.gson.annotations.SerializedName

data class PhoneBody(
    @SerializedName("phone")
    val phone: String, // max length: 30
    @SerializedName("code")
    val code: String
)