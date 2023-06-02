package com.example.domain.models

data class Auth(
    val refreshToken: String? = null,
    val accessToken: String? = null,
    val userId: Long? = null,
    val isUserExists: Boolean
)