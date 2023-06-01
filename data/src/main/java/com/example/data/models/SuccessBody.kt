package com.example.data.models

import com.google.gson.annotations.SerializedName

data class SuccessBody(
    @SerializedName("is_success")
    val isSuccess: Boolean
)