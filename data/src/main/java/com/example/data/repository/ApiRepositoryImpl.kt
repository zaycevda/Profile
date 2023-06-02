package com.example.data.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.CountDownTimer
import com.example.data.service.Api
import com.example.data.utils.MyCountDownTimer
import com.example.data.utils.toAuth
import com.example.data.utils.toAvatars
import com.example.data.utils.toPhoneBody
import com.example.data.utils.toPhoneShortBody
import com.example.data.utils.toRefreshTokenBody
import com.example.data.utils.toSuccess
import com.example.data.utils.toTokens
import com.example.data.utils.toUser
import com.example.data.utils.toUserMiniBody
import com.example.data.utils.toUserShortBody
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
import com.example.domain.repository.ApiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ApiRepositoryImpl(private val api: Api, context: Context) : ApiRepository {

    private lateinit var refreshToken: String
    private var countDownTimer: CountDownTimer? = null

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override suspend fun auth(phoneShort: PhoneShort): Success {
        val phoneShortBody = phoneShort.toPhoneShortBody()
        val successBody = api.auth(phoneShortBody = phoneShortBody)
        return successBody.toSuccess()
    }

    override suspend fun checkAuth(phone: Phone): Auth {
        val phoneBody = phone.toPhoneBody()
        val authBody = api.checkAuth(phoneBody = phoneBody)

        editor.putString(REFRESH_TOKEN, authBody.refreshToken)
        editor.putString(ACCESS_TOKEN, authBody.accessToken)
        editor.apply()
        startTokenRefreshTimer()

        return authBody.toAuth()
    }

    override suspend fun getRefreshToken(refreshToken: RefreshToken): Tokens {
        val refreshTokenBody = refreshToken.toRefreshTokenBody()
        val tokensBody = api.getRefreshToken(refreshTokenBody = refreshTokenBody)

        editor.putString(ACCESS_TOKEN, tokensBody.accessToken)
        editor.apply()
        startTokenRefreshTimer()

        return tokensBody.toTokens()
    }

    override suspend fun getToken(): String {
        editor.putString(ACCESS_TOKEN, api.getToken())
        editor.apply()
        startTokenRefreshTimer()

        refreshToken = api.getToken()
        return refreshToken
    }

    override fun getUser(): Flow<User> {
        val userBody = api.getUser()
        return userBody.map { it.profileDataBody.toUser() }
    }

    override suspend fun register(userMini: UserMini): Tokens {
        val userMiniBody = userMini.toUserMiniBody()
        val tokensBody = api.register(userMiniBody = userMiniBody)

        editor.putString(REFRESH_TOKEN, tokensBody.refreshToken)
        editor.putString(ACCESS_TOKEN, tokensBody.accessToken)
        editor.apply()
        startTokenRefreshTimer()

        return tokensBody.toTokens()
    }

    override suspend fun updateUser(userShort: UserShort): Avatars {
        val userShortBody = userShort.toUserShortBody()
        val avatarsObjectBody = api.updateUser(userShortBody = userShortBody)
        val avatarsBody = avatarsObjectBody.avatarsBody
        return avatarsBody.toAvatars()
    }

    private fun startTokenRefreshTimer() {
        countDownTimer?.cancel()

        countDownTimer = MyCountDownTimer {
            CoroutineScope(Dispatchers.IO).launch {
                sharedPreferences.getString(REFRESH_TOKEN, BLANK)?.let {
                    val refreshToken = RefreshToken(refreshToken = it)
                    getRefreshToken(refreshToken = refreshToken)
                }
            }
        }

        countDownTimer?.start()
    }

    companion object {
        private const val REFRESH_TOKEN = "REFRESH_TOKEN"
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val BLANK = ""
        const val SHARED_PREFERENCES = "SHARED_PREFERENCES"
    }
}