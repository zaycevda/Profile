package com.example.domain.models

data class Avatars(
    val avatar: String? = null,
    val bigAvatar: String,
    val miniAvatar: String
) {
    companion object {
        fun emptyAvatars() =
            Avatars(
                bigAvatar = "",
                miniAvatar = ""
            )
    }
}