package com.example.data.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.data.repository.ApiRepositoryImpl.Companion.ACCESS_TOKEN
import com.example.data.repository.ApiRepositoryImpl.Companion.BLANK
import com.example.data.repository.ApiRepositoryImpl.Companion.SHARED_PREFERENCES
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(context: Context) : Interceptor {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE)

    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        if (shouldAddToken(originalRequest.url.toString()))
            sharedPreferences.getString(ACCESS_TOKEN, BLANK)?.let { accessToken ->
                val modifiedRequest = requestBuilder
                    .addHeader(AUTHORIZATION, "$BEARER $accessToken")
                    .build()
                return@let chain.proceed(modifiedRequest)
            }

        return chain.proceed(originalRequest)
    }

    private fun shouldAddToken(url: String) =
        !(url.contains(REGISTER) ||
        url.contains(SEND_AUTH_CODE) ||
        url.contains(CHECK_AUTH_CODE))

    private companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val BEARER = "Bearer"
        private const val REGISTER = "register"
        private const val SEND_AUTH_CODE = "send-auth-code"
        private const val CHECK_AUTH_CODE = "check-auth-code"
    }
}