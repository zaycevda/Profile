package com.example.data.models

import com.google.gson.annotations.SerializedName

data class PhoneShortBody(
    @SerializedName("phone")
    val phone: String
)