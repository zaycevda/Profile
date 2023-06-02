package com.example.domain.models

data class UserShort(
    val name: String,
    val username: String,
    val birthday: String,
    val city: String,
    val vk: String,
    val instagram: String,
    val status: String,
    val avatar: Avatar
)

data class Avatar(
    val filename: String,
    val base64: String
)