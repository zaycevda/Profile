package com.example.domain.models

data class User(
    val name: String,
    val username: String,
    val birthday: String,
    val city: String,
    val vk: String,
    val instagram: String,
    val status: String,
    val avatar: String,
    val id: Long,
    val last: String,
    val online: Boolean,
    val created: String,
    val phone: String,
    val completedTask: Long,
    val avatars: Avatars
)