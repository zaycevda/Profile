package com.example.domain.models

data class UserMini(
    val phone: String,
    val name: String, // maxLength = 128
    val username: String // minLength = 5, maxLength = 32
)