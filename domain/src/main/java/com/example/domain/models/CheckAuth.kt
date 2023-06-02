package com.example.domain.models

data class CheckAuth(
    val phoneNumber: String, // max length: 30
    val code: String
)