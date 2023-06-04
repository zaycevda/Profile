package com.example.domain.repository

import com.example.domain.models.Auth
import com.example.domain.models.Avatars
import com.example.domain.models.CheckAuth
import com.example.domain.models.Phone
import com.example.domain.models.Success
import com.example.domain.models.Tokens
import com.example.domain.models.User
import com.example.domain.models.UserMini
import com.example.domain.models.UserShort

interface ApiRepository {
    suspend fun auth(phone: Phone): Success

    suspend fun checkAuth(checkAuth: CheckAuth): Auth

    suspend fun getToken(): String

    suspend fun getUser(): User

    suspend fun registration(userMini: UserMini): Tokens

    suspend fun editUser(userShort: UserShort): Avatars?
}