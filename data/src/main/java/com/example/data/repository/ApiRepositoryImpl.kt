package com.example.data.repository

import android.content.Context
import android.util.Log
import com.example.data.service.Api
import com.example.data.utils.TokenSharedPrefs
import com.example.data.utils.toAuth
import com.example.data.utils.toAvatars
import com.example.data.utils.toPhoneBody
import com.example.data.utils.toPhoneShortBody
import com.example.data.utils.toSuccess
import com.example.data.utils.toTokens
import com.example.data.utils.toUser
import com.example.data.utils.toUserMiniBody
import com.example.data.utils.toUserShortBody
import com.example.domain.models.Auth
import com.example.domain.models.Avatars
import com.example.domain.models.CheckAuth
import com.example.domain.models.Phone
import com.example.domain.models.Success
import com.example.domain.models.Tokens
import com.example.domain.models.User
import com.example.domain.models.UserMini
import com.example.domain.models.UserShort
import com.example.domain.repository.ApiRepository

class ApiRepositoryImpl(private val api: Api, context: Context) : ApiRepository {

    private val tokenSharedPrefs = TokenSharedPrefs(context)

    override suspend fun auth(phone: Phone): Success {
        val phoneShortBody = phone.toPhoneShortBody()
        val successBody = api.auth(phoneBody = phoneShortBody)

        Log.d(TAG, "checkAuth: successBody = $successBody")

        return successBody.toSuccess()
    }

    override suspend fun checkAuth(checkAuth: CheckAuth): Auth {
        val checkAuthBody = checkAuth.toPhoneBody()
        val authBody = api.checkAuth(checkAuthBody = checkAuthBody)

        tokenSharedPrefs.refreshToken = authBody.refreshToken
        tokenSharedPrefs.accessToken = authBody.accessToken

        Log.d(TAG, "checkAuth: authBody = $authBody")

        return authBody.toAuth()
    }

    override suspend fun getToken(): String {
        val accessToken = api.getToken()

        tokenSharedPrefs.accessToken = accessToken

        Log.d(TAG, "getToken: accessToken = $accessToken")

        return accessToken
    }

    override suspend fun getUser(): User {
        val userBody = api.getUser()

        Log.d(TAG, "getUser: userBody = $userBody")

        return userBody.profileDataBody.toUser()
    }

    override suspend fun registration(userMini: UserMini): Tokens {
        val userMiniBody = userMini.toUserMiniBody()
        val tokensBody = api.registration(userMiniBody = userMiniBody)

        tokenSharedPrefs.refreshToken = tokensBody.refreshToken
        tokenSharedPrefs.accessToken = tokensBody.accessToken

        Log.d(TAG, "register: tokensBody = $tokensBody")

        return tokensBody.toTokens()
    }

    override suspend fun updateUser(userShort: UserShort): Avatars {
        val userShortBody = userShort.toUserShortBody()
        val avatarsObjectBody = api.updateUser(userShortBody = userShortBody)
        val avatarsBody = avatarsObjectBody.avatarsBody

        Log.d(TAG, "updateUser: avatarsBody = $avatarsBody")

        return avatarsBody.toAvatars()
    }

    private companion object {
        private const val TAG = "ApiRepositoryImpl"
    }
}