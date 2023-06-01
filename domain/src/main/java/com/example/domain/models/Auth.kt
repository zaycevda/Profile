package com.example.domain.models

data class Auth(
    val refreshToken: String,
    val accessToken: String,
    val userId: Long,
    val isUserExists: Boolean
)