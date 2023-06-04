package com.example.data.service

import com.example.data.models.AuthBody
import com.example.data.models.AvatarsObjectBody
import com.example.data.models.CheckAuthBody
import com.example.data.models.PhoneBody
import com.example.data.models.RefreshTokenBody
import com.example.data.models.SuccessBody
import com.example.data.models.TokensBody
import com.example.data.models.UserBody
import com.example.data.models.UserMiniBody
import com.example.data.models.UserShortBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT

interface Api {
    @POST(API_AUTH)
    suspend fun auth(@Body phoneBody: PhoneBody): SuccessBody

    @POST(API_CHECK_AUTH)
    suspend fun checkAuth(@Body checkAuthBody: CheckAuthBody): AuthBody

    @POST(API_GET_REFRESH_TOKEN)
    fun getRefreshToken(@Body refreshTokenBody: RefreshTokenBody): Call<TokensBody>

    @GET(API_GET_TOKEN)
    suspend fun getToken(): String

    @Headers("$CACHE_CONTROL$CACHE_LIFESPAN")
    @GET(API_GET_USER)
    suspend fun getUser(): UserBody

    @POST(API_REGISTER)
    suspend fun registration(@Body userMiniBody: UserMiniBody): TokensBody

    @PUT(API_EDIT_USER)
    suspend fun editUser(@Body userShortBody: UserShortBody): AvatarsObjectBody

    private companion object {
        private const val API_AUTH = "api/v1/users/send-auth-code/"
        private const val API_CHECK_AUTH = "api/v1/users/check-auth-code/"
        private const val API_GET_REFRESH_TOKEN = "api/v1/users/refresh-token/"
        private const val API_GET_TOKEN = "api/v1/users/check-jwt/"
        private const val API_GET_USER = "api/v1/users/me/"
        private const val API_REGISTER = "api/v1/users/register/"
        private const val API_EDIT_USER = "api/v1/users/me/"
        private const val CACHE_CONTROL = "Cache-Control: max-age="
        private const val CACHE_LIFESPAN = "600" // 10 minutes
    }
}