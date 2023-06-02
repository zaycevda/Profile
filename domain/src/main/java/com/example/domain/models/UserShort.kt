package com.example.domain.models

data class UserShort(
    val name: String,
    val username: String,
    val birthday: String? = null,
    val city: String? = null,
    val vk: String? = null,
    val instagram: String? = null,
    val status: String? = null,
    val avatar: Avatar? = null
)

data class Avatar(
    val filename: String,
    val base64: String
)