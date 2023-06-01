package com.example.data.service

import com.example.data.models.AuthBody
import com.example.data.models.AvatarsObjectBody
import com.example.data.models.PhoneBody
import com.example.data.models.PhoneShortBody
import com.example.data.models.RefreshTokenBody
import com.example.data.models.SuccessBody
import com.example.data.models.TokensBody
import com.example.data.models.UserBody
import com.example.data.models.UserMiniBody
import com.example.data.models.UserShortBody
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface Api {
    @POST("users/send-auth-code/")
    suspend fun auth(@Body phoneShortBody: PhoneShortBody): SuccessBody

    @POST("users/check-auth-code/")
    suspend fun checkAuth(@Body phoneBody: PhoneBody): AuthBody

    @POST("users/refresh-token/")
    suspend fun getRefreshToken(@Body refreshTokenBody: RefreshTokenBody): TokensBody

    @GET("users/check-jwt/")
    suspend fun getToken(): String

    @GET("users/me/")
    fun getUser(): Flow<UserBody>

    @POST("users/register/")
    suspend fun register(@Body userMiniBody: UserMiniBody): TokensBody

    @PUT("users/me/")
    suspend fun updateUser(@Body userShortBody: UserShortBody): AvatarsObjectBody
}