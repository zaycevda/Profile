package com.example.domain.models

data class Tokens(
    val refreshToken: String,
    val accessToken: String,
    val userId: Long
)