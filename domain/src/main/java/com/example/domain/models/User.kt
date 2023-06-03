package com.example.domain.models

data class User(
    val name: String,
    val username: String,
    val birthday: String? = null,
    val city: String? = null,
    val vk: String? = null,
    val instagram: String? = null,
    val status: String? = null,
    val avatar: String? = null,
    val id: Long,
    val last: String? = null,
    val online: Boolean,
    val created: String? = null,
    val phone: String,
    val completedTask: Long = 0,
    val avatars: Avatars? = null
) {
    companion object {
        fun emptyUser() =
            User(
                name = "",
                username = "",
                id = 0,
                online = false,
                phone = ""
            )
    }
}