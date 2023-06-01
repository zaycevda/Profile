package com.example.domain.repository

import com.example.domain.models.Auth
import com.example.domain.models.Avatars
import com.example.domain.models.Phone
import com.example.domain.models.PhoneShort
import com.example.domain.models.RefreshToken
import com.example.domain.models.Success
import com.example.domain.models.Tokens
import com.example.domain.models.User
import com.example.domain.models.UserMini
import com.example.domain.models.UserShort
import kotlinx.coroutines.flow.Flow

interface ApiRepository {
    suspend fun auth(phoneShort: PhoneShort): Success

    suspend fun checkAuth(phone: Phone): Auth

    suspend fun getRefreshToken(refreshToken: RefreshToken): Tokens

    suspend fun getToken(): String

    fun getUser(): Flow<User>

    suspend fun register(userMini: UserMini): Tokens

    suspend fun updateUser(userShort: UserShort): Avatars
}