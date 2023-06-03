package com.example.data.utils

import com.example.data.models.RefreshTokenBody
import com.example.data.service.Api

class TokenProvider(
    private val tokenSharedPrefs: TokenSharedPrefs,
    private val api: Api
) {
    fun refreshToken() {
        val refreshToken = tokenSharedPrefs.refreshToken

        val call = api.getRefreshToken(RefreshTokenBody(refreshToken))
        val response = call.execute()

        if (response.isSuccessful) {
            val tokenResponse = response.body()
            tokenResponse?.let {
                tokenSharedPrefs.accessToken = it.accessToken
                tokenSharedPrefs.refreshToken = it.refreshToken
            }
        }
    }
}