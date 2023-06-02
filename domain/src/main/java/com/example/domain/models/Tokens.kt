package com.example.domain.models

data class Tokens(
    val refreshToken: String? = null,
    val accessToken: String? = null,
    val userId: Long? = null
)